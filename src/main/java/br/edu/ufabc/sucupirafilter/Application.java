/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sucupirafilter;

/**
 *
 * @author isaac
 */


import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.ufabc.sucupirafilter.model.AreaAvaliacao;
import br.edu.ufabc.sucupirafilter.model.AreaConhecimento;
import br.edu.ufabc.sucupirafilter.model.Curso;
import br.edu.ufabc.sucupirafilter.model.Instituicao;
import br.edu.ufabc.sucupirafilter.model.Programa;

import br.edu.ufabc.sucupirafilter.repository.AreaAvaliacaoRepository;
import br.edu.ufabc.sucupirafilter.repository.AreaConhecimentoRepository;
import br.edu.ufabc.sucupirafilter.repository.CursoRepository;
import br.edu.ufabc.sucupirafilter.repository.InstituicaoRepository;
import br.edu.ufabc.sucupirafilter.repository.ProgramaRepository;

@SpringBootApplication
public class Application {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
    public static Properties getProp(String fileName) throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(fileName);
        props.load(file);
        return props;
    }
    
    public static String getPage(URL url) throws IOException {
        
        String page = "";
        String inputLine;
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        while ((inputLine = in.readLine()) != null) {
            page += (inputLine + "\n");
        }
        
        in.close();
        return page;
        
    }
    
    @Bean
    public CommandLineRunner runner() {
        
        return (args) -> {
            
            log.info("Sucupira Filter");
            // TODO
            
        };
        
    }
    
}
