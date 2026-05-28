package com.empresa.pinturas.model;

import java.io.Serializable;
import java.util.Objects;

public class ProductoEnvasadoId implements Serializable {

    private String producto;   // código con letras
    private Integer envasado; 
    
    public ProductoEnvasadoId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoEnvasadoId)) return false;
        ProductoEnvasadoId that = (ProductoEnvasadoId) o;
        return Objects.equals(producto, that.producto) &&
               Objects.equals(envasado, that.envasado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producto, envasado);
    }
}
