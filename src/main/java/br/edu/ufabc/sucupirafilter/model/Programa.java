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

public class Programa implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    private Long id;
    private String nome;
    private String codigo;
    private Instituicao instituicao;
    
    public Programa() {
        
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
    
    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    
    public Instituicao getInstituicao() {
        return this.instituicao;
    }
    
}
