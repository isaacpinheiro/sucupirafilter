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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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
    
    public static Properties getProps(String fileName) throws IOException {
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
    
    public static void buscarPorAreaAvaliacao() throws IOException {
        
        log.info("Buscando as informações da Área de Avaliação e salvando no Banco de Dados.");
        Properties urlProps = getProps("src/main/resources/url.properties");
        Properties regexProps = getProps("src/main/resources/regex.properties");
        
        String areaAvaliacao = urlProps.getProperty("url.areaAvaliacao");
        String areaAvaliacaoCurso = urlProps.getProperty("url.areaAvaliacaoCurso");
        
        try {
            
            URL url = new URL(areaAvaliacao);
            String page = getPage(url);
            
            Matcher matcher;
            
            Pattern tab1 = Pattern.compile(regexProps.getProperty("regex.tab1"));
            Pattern tab2 = Pattern.compile(regexProps.getProperty("regex.tab2"));
            Pattern newline = Pattern.compile(regexProps.getProperty("regex.newline"));
            Pattern divResultado = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.divResultado"));
            Pattern table = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.table"));
            Pattern tbody = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.tbody"));
            
            matcher = tab1.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = tab2.matcher(page);
            page = matcher.replaceAll("<");
            
            matcher = newline.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = divResultado.matcher(page);
            matcher.find();
            
            matcher = table.matcher(page);
            matcher.find();
            
            matcher = tbody.matcher(page);
            matcher.find();
            page = matcher.group();
            
            log.info(page);
            
        } catch (MalformedURLException mue) {
            
            log.info(mue.getMessage());
            
        } catch (Exception e) {
            
            log.info(e.getMessage());
            
        }
        
    }
    
    @Bean
    public CommandLineRunner runner(AreaAvaliacaoRepository aar, AreaConhecimentoRepository acr,
        CursoRepository cr, InstituicaoRepository ir, ProgramaRepository pr) {
        
        return (args) -> {
            
            log.info("Sucupira Filter");
            buscarPorAreaAvaliacao();
            
        };
        
    }
    
}
