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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;

@Entity
@Table(name = "programa")
public class Programa implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "uf")
    private String uf;
    
    @ManyToOne
    @JoinColumn(name = "area_conhecimento_id", referencedColumnName = "id")
    private AreaConhecimento areaConhecimento;
    
    @ManyToOne
    @JoinColumn(name = "instituicao_id", referencedColumnName = "id")
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
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getUf() {
        return this.uf;
    }
    
    public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }
    
    public AreaConhecimento getAreaConhecimento() {
        return this.areaConhecimento;
    }
    
    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    
    public Instituicao getInstituicao() {
        return this.instituicao;
    }
    
}
