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
import javax.persistence.Column;

@Entity
@Table(name = "instituicao")
public class Instituicao implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "acronimo")
    private String acronimo;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "distrito")
    private String distrito;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "cep")
    private String cep;
    
    @Column(name = "caixa_postal")
    private String caixaPostal;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "coordenandas")
    private String coordenadas;
    
    public Instituicao() {
        
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
    
    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }
    
    public String getAcronimo() {
        return this.acronimo;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getEndereco() {
        return this.endereco;
    }
    
    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    
    public String getDistrito() {
        return this.distrito;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getCidade() {
        return this.cidade;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getCep() {
        return this.cep;
    }
    
    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }
    
    public String getCaixaPostal() {
        return this.caixaPostal;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
    
    public String getCoordenadas() {
        return this.coordenadas;
    }
    
}
