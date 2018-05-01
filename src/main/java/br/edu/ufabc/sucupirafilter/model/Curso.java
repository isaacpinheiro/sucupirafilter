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
@Table(name = "curso")
public class Curso implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "situacao")
    private String situacao;
    
    @Column(name = "nivel")
    private String nivel;
    
    @Column(name = "nota_curso")
    private String notaCurso;
    
    @Column(name = "data_recomendacao")
    private String dataRecomendacao;
    
    @Column(name = "data_inicio")
    private String dataInicio;
    
    @ManyToOne
    @JoinColumn(name = "programa_id", referencedColumnName = "id")
    private Programa programa;
    
    public Curso() {
        
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
    
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    public String getSituacao() {
        return this.situacao;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public String getNivel() {
        return this.nivel;
    }
    
    public void setNotaCurso(String notaCurso) {
        this.notaCurso = notaCurso;
    }
    
    public String getNotaCurso() {
        return this.notaCurso;
    }
    
    public void setDataRecomendacao(String dataRecomendacao) {
        this.dataRecomendacao = dataRecomendacao;
    }
    
    public String getDataRecomendacao() {
        return this.dataRecomendacao;
    }
    
    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public String getDataInicio() {
        return this.dataInicio;
    }
    
    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
    
    public Programa getPrograma() {
        return this.programa;
    }
    
}
