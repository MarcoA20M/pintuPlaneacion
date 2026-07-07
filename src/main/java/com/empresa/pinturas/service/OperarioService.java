package com.empresa.pinturas.service;

import com.empresa.pinturas.model.Operario;
import com.empresa.pinturas.repository.OperarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperarioService {
    
    @Autowired
    private OperarioRepository operarioRepository;
    
    private static final Map<String, int[]> GRUPOS_MAQUINAS = new LinkedHashMap<>();
    private static final List<String> GRUPOS_ORDEN = Arrays.asList("grupo0", "grupo1", "grupo2", "grupo3");
    
    static {
        GRUPOS_MAQUINAS.put("grupo0", new int[]{101, 102});
        GRUPOS_MAQUINAS.put("grupo1", new int[]{103, 104});
        GRUPOS_MAQUINAS.put("grupo2", new int[]{105, 106});
        GRUPOS_MAQUINAS.put("grupo3", new int[]{107, 108});
    }
    
    // ========== OBTENER OPERARIOS ==========
    public List<Operario> getVinilica() {
        return operarioRepository.findByAreaAndActivoTrueOrderByOrdenVinilicaAsc("vinilica");
    }
    
    public List<Operario> getEsmaltes() {
        return operarioRepository.findByAreaAndActivoTrue("esmaltes");
    }
    
    public List<Operario> getEspeciales() {
        return operarioRepository.findByAreaAndActivoTrue("especial");
    }
    
    // ========== CRUD ==========
    public Operario crear(Operario operario) {
        if ("vinilica".equals(operario.getArea())) {
            long count = operarioRepository.count();
            operario.setOrdenVinilica((int) count);
        }
        return operarioRepository.save(operario);
    }
    
    public Operario actualizar(Integer id, Operario data) {
        Operario existente = operarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        existente.setNombre(data.getNombre());
        existente.setPuesto(data.getPuesto());
        existente.setArea(data.getArea());
        existente.setActivo(data.getActivo());
        existente.setOrdenVinilica(data.getOrdenVinilica());
        return operarioRepository.save(existente);
    }
    
    public void eliminar(Integer id) {
        operarioRepository.deleteById(id);
    }
    
    public Operario toggleActivo(Integer id) {
        Operario existente = operarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        existente.setActivo(!existente.getActivo());
        return operarioRepository.save(existente);
    }
    
    @Transactional
    public List<Operario> reordenarVinilica(List<Integer> ids) {
        List<Operario> operarios = operarioRepository.findByAreaAndActivoTrue("vinilica");
        Map<Integer, Operario> mapa = new HashMap<>();
        for (Operario op : operarios) {
            mapa.put(op.getId(), op);
        }
        for (int i = 0; i < ids.size(); i++) {
            if (mapa.containsKey(ids.get(i))) {
                Operario op = mapa.get(ids.get(i));
                op.setOrdenVinilica(i);
                operarioRepository.save(op);
            }
        }
        return operarioRepository.findByAreaAndActivoTrueOrderByOrdenVinilicaAsc("vinilica");
    }
    
    // ========== 🔄 ROTACIÓN ==========
    
    /**
     * 🔴 Obtiene el orden base (sin rotación)
     */
    public Map<Integer, String> getBase() {
        List<Operario> operarios = getVinilica();
        Map<Integer, String> resultado = new LinkedHashMap<>();
        
        if (operarios.isEmpty()) {
            return resultado;
        }
        
        List<Operario> operariosOrdenados = operarios.stream()
                .sorted(Comparator.comparingInt(Operario::getOrdenVinilica))
                .collect(Collectors.toList());
        
        List<String> nombresBase = operariosOrdenados.stream()
                .map(Operario::getNombre)
                .collect(Collectors.toList());
        
        System.out.println("📌 ORDEN BASE: " + nombresBase);
        
        for (int i = 0; i < GRUPOS_ORDEN.size() && i < nombresBase.size(); i++) {
            String nombre = nombresBase.get(i);
            int[] maquinas = GRUPOS_MAQUINAS.get(GRUPOS_ORDEN.get(i));
            for (int maq : maquinas) {
                resultado.put(maq, nombre);
            }
        }
        
        return resultado;
    }
    
    /**
     * 🔴 Aplica rotación según el número de semanas
     * Semana 0: [Luis22, juanton, Pedro, Isaac1]
     * Semana 1: [Isaac1, Luis22, juanton, Pedro]
     * Semana 2: [Pedro, Isaac1, Luis22, juanton]
     * Semana 3: [juanton, Pedro, Isaac1, Luis22]
     */
    public Map<Integer, String> rotar(int semanas) {
        System.out.println("🔄 ROTAR: Aplicando " + semanas + " semanas de rotación");
        
        List<Operario> operarios = getVinilica();
        Map<Integer, String> resultado = new LinkedHashMap<>();
        
        if (operarios.isEmpty()) {
            System.out.println("⚠️ No hay operarios");
            return resultado;
        }
        
        // Obtener orden base
        List<Operario> operariosOrdenados = operarios.stream()
                .sorted(Comparator.comparingInt(Operario::getOrdenVinilica))
                .collect(Collectors.toList());
        
        List<String> nombresBase = operariosOrdenados.stream()
                .map(Operario::getNombre)
                .collect(Collectors.toList());
        
        System.out.println("📌 ORDEN BASE: " + nombresBase);
        
        // Si semanas es 0, devolver base
        if (semanas == 0) {
            System.out.println("📌 SEMANA 0: Sin rotación");
            for (int i = 0; i < GRUPOS_ORDEN.size() && i < nombresBase.size(); i++) {
                String nombre = nombresBase.get(i);
                int[] maquinas = GRUPOS_MAQUINAS.get(GRUPOS_ORDEN.get(i));
                for (int maq : maquinas) {
                    resultado.put(maq, nombre);
                }
            }
            return resultado;
        }
        
        // Aplicar rotación
        List<String> nombresRotados = new ArrayList<>(nombresBase);
        long rotaciones = semanas % nombresRotados.size();
        
        System.out.println("🔄 Rotaciones a aplicar: " + rotaciones);
        
        for (int i = 0; i < rotaciones; i++) {
            // Mover el último al primero
            String ultimo = nombresRotados.remove(nombresRotados.size() - 1);
            nombresRotados.add(0, ultimo);
            System.out.println("  Rotación " + (i + 1) + ": " + nombresRotados);
        }
        
        System.out.println("📌 ORDEN ROTADO (semana " + semanas + "): " + nombresRotados);
        
        // Asignar a máquinas
        for (int i = 0; i < GRUPOS_ORDEN.size() && i < nombresRotados.size(); i++) {
            String nombre = nombresRotados.get(i);
            int[] maquinas = GRUPOS_MAQUINAS.get(GRUPOS_ORDEN.get(i));
            for (int maq : maquinas) {
                resultado.put(maq, nombre);
            }
        }
        
        System.out.println("📋 RESULTADO FINAL: " + resultado);
        
        return resultado;
    }
    
    // ========== MÉTODOS LEGACY ==========
    
    public Map<Integer, String> getRotacion(int semanas, boolean sinRotacion) {
        if (sinRotacion || semanas == 0) {
            return getBase();
        }
        return rotar(semanas);
    }
    
    public Map<Integer, String> getRotacion(LocalDate fecha, boolean sinRotacion) {
        return getBase();
    }
    
    public Map<Integer, String> getRotacion(LocalDate fecha) {
        return getBase();
    }
    
    public Map<Integer, String> getRotacionActual() {
        return getBase();
    }
    
    public Map<Integer, String> getRotacionBase() {
        return getBase();
    }
    
    public Map<String, Object> getConfiguracionVinilica() {
        Map<String, Object> config = new HashMap<>();
        List<Operario> operarios = getVinilica();
        config.put("operarios", operarios);
        config.put("fechaAnclaje", LocalDate.of(2026, 1, 5));
        
        Map<String, Map<String, Object>> gruposBase = new LinkedHashMap<>();
        String[] gruposNombres = {"grupo0", "grupo1", "grupo2", "grupo3"};
        int[][] maquinasPorGrupo = {{101, 102}, {103, 104}, {105, 106}, {107, 108}};
        String[] nombresGrupos = {
            "Grupo 0 (VI-101, VI-102)",
            "Grupo 1 (VI-103, VI-104)",
            "Grupo 2 (VI-105, VI-106)",
            "Grupo 3 (VI-107, VI-108)"
        };
        
        List<Operario> operariosOrdenados = operarios.stream()
                .sorted(Comparator.comparingInt(Operario::getOrdenVinilica))
                .collect(Collectors.toList());
        
        for (int i = 0; i < gruposNombres.length; i++) {
            Map<String, Object> grupo = new HashMap<>();
            List<Integer> maquinas = new ArrayList<>();
            for (int maq : maquinasPorGrupo[i]) {
                maquinas.add(maq);
            }
            grupo.put("maquinas", maquinas);
            grupo.put("nombre", nombresGrupos[i]);
            if (i < operariosOrdenados.size()) {
                grupo.put("operarioId", operariosOrdenados.get(i).getId());
            } else {
                grupo.put("operarioId", null);
            }
            gruposBase.put(gruposNombres[i], grupo);
        }
        
        config.put("gruposBase", gruposBase);
        config.put("ordenBase", getBase());
        config.put("rotacionActual", getBase());
        config.put("semanasRotadas", 0);
        
        return config;
    }
}