package com.empresa.pinturas.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.pinturas.model.Familia;
import com.empresa.pinturas.repository.FamiliaRepository;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin(origins = "http://localhost:3000")
public class FamiliaController {

    private final FamiliaRepository familiaRepository;
    
    // En FamiliaController.java, actualiza el constructor y el método subirImagen

@Value("${app.upload.dir:src/uploads/familias/}")
private String uploadDir;

public FamiliaController(FamiliaRepository familiaRepository) {
    this.familiaRepository = familiaRepository;
    
    // Crear directorio de imágenes si no existe
    try {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("📁 Directorio creado: " + uploadPath.toAbsolutePath());
        } else {
            System.out.println("📁 Directorio existente: " + uploadPath.toAbsolutePath());
        }
    } catch (Exception e) {
        System.err.println("❌ Error creando directorio: " + e.getMessage());
    }
}
    // ========== MÉTODOS GET ==========
    
    @GetMapping
    public ResponseEntity<List<Familia>> getAll() {
        List<Familia> familias = familiaRepository.findAll();
        return ResponseEntity.ok(familias);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Familia>> getByTipo(@PathVariable String tipo) {
        List<Familia> familias = familiaRepository.findByTipo(tipo);
        return ResponseEntity.ok(familias);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Familia> getById(@PathVariable Long id) {
        Optional<Familia> familia = familiaRepository.findById(id);
        return familia.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // ========== MÉTODO PARA ACTUALIZAR NOMBRE ==========
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarFamilia(@PathVariable Long id, @RequestBody Familia familiaActualizada) {
        try {
            Optional<Familia> familiaOpt = familiaRepository.findById(id);
            
            if (!familiaOpt.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Familia no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            Familia familia = familiaOpt.get();
            
            // Actualizar nombre si viene en el body
            if (familiaActualizada.getNombre() != null && !familiaActualizada.getNombre().isEmpty()) {
                familia.setNombre(familiaActualizada.getNombre());
            }
            
            familiaRepository.save(familia);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Nombre actualizado correctamente");
            response.put("familia", familia);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // ========== MÉTODOS PARA IMÁGENES ==========
    
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> subirImagen(@PathVariable Long id, @RequestParam("imagen") MultipartFile archivo) {
        try {
            Optional<Familia> familiaOpt = familiaRepository.findById(id);
            if (!familiaOpt.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Familia no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            if (archivo.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "No se ha seleccionado ningún archivo");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Validar tipo de archivo
            String contentType = archivo.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Solo se permiten imágenes (JPG, PNG, GIF)");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Validar tamaño máximo (2MB)
            if (archivo.getSize() > 2 * 1024 * 1024) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El archivo es demasiado grande. Máximo 2MB");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Eliminar imagen anterior si existe
            eliminarArchivoImagen(id);
            
            // Obtener extensión del archivo
            String extension = "";
            String nombreOriginal = archivo.getOriginalFilename();
            if (nombreOriginal != null && nombreOriginal.contains(".")) {
                extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            } else {
                extension = ".jpg";
            }
            
            // Guardar nueva imagen
            String nombreArchivo = "familia_" + id + extension;
            Path rutaCompleta = Paths.get(uploadDir + nombreArchivo);
            Files.write(rutaCompleta, archivo.getBytes());
            
            System.out.println("✅ Imagen guardada: " + rutaCompleta.toAbsolutePath());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Imagen guardada correctamente");
            response.put("url", "/api/familias/" + id + "/imagen");
            response.put("filename", nombreArchivo);
            
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            System.err.println("❌ Error guardando imagen: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al guardar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/{id}/imagen")
    public ResponseEntity<?> obtenerImagen(@PathVariable Long id) {
        try {
            File directorio = new File(uploadDir);
            if (directorio.exists()) {
                File[] archivos = directorio.listFiles((dir, nombre) -> 
                    nombre.startsWith("familia_" + id + "."));
                
                if (archivos != null && archivos.length > 0) {
                    Path ruta = Paths.get(archivos[0].getAbsolutePath());
                    byte[] imagenBytes = Files.readAllBytes(ruta);
                    
                    // Determinar content type
                    String contentType = "image/jpeg";
                    String nombreArchivo = archivos[0].getName();
                    if (nombreArchivo.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (nombreArchivo.endsWith(".gif")) {
                        contentType = "image/gif";
                    }
                    
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(imagenBytes);
                }
            }
            
            // Si no hay imagen, devolver 404
            return ResponseEntity.notFound().build();
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}/imagen")
    public ResponseEntity<?> eliminarImagen(@PathVariable Long id) {
        try {
            boolean eliminada = eliminarArchivoImagen(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", eliminada ? "Imagen eliminada correctamente" : "No existía imagen para eliminar");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // ========== ELIMINAR FAMILIA COMPLETA ==========
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFamilia(@PathVariable Long id) {
        try {
            Optional<Familia> familiaOpt = familiaRepository.findById(id);
            if (!familiaOpt.isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Familia no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            // Eliminar imagen asociada
            eliminarArchivoImagen(id);
            
            // Eliminar familia de la base de datos
            familiaRepository.deleteById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Familia eliminada correctamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la familia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // ========== MÉTODO PRIVADO AUXILIAR ==========
    
    private boolean eliminarArchivoImagen(Long id) {
        File directorio = new File(uploadDir);
        if (directorio.exists()) {
            File[] archivos = directorio.listFiles((dir, nombre) -> 
                nombre.startsWith("familia_" + id + "."));
            
            if (archivos != null && archivos.length > 0) {
                boolean eliminado = archivos[0].delete();
                if (eliminado) {
                    System.out.println("🗑️ Imagen eliminada: " + archivos[0].getName());
                }
                return eliminado;
            }
        }
        return false;
    }
}