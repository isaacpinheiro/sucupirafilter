/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sucupirafilter.model;

/**
 *
 * @author isaac
 */

import java.io.Serializable;

public class AreaConhecimento implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private Long id;
    private String nome;
    private String codigo;
    private AreaAvaliacao areaAvaliacao;
    
    public AreaConhecimento() {
        
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setAreaAvaliacao(AreaAvaliacao areaAvaliacao) {
        this.areaAvaliacao = areaAvaliacao;
    }
    
    public AreaAvaliacao getAreaAvaliacao() {
        return this.areaAvaliacao;
    }
    
}
