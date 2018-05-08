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
    
    public static void buscarPorAreaAvaliacao(AreaAvaliacaoRepository aar, AreaConhecimentoRepository acr,
        InstituicaoRepository ir) throws IOException {
        
        log.info("Buscando as informações da Área de Avaliação e salvando no Banco de Dados.");
        Properties urlProps = getProps("src/main/resources/url.properties");
        Properties regexProps = getProps("src/main/resources/regex.properties");
        
        String sucupira = urlProps.getProperty("url.sucupira");
        String areaAvaliacao = urlProps.getProperty("url.areaAvaliacao");
        String codigo = urlProps.getProperty("url.codigo");
        
        try {
            
            URL url = new URL(sucupira + areaAvaliacao);
            String page = getPage(url);
            
            Matcher matcher, matcher2;
            
            Pattern tab1 = Pattern.compile(regexProps.getProperty("regex.tab1"));
            Pattern tab2 = Pattern.compile(regexProps.getProperty("regex.tab2"));
            Pattern newline = Pattern.compile(regexProps.getProperty("regex.newline"));
            Pattern divResultado = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.divResultado"));
            Pattern table = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.table"));
            Pattern tbody = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.tbody"));
            Pattern tr = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.tr"));
            Pattern trReplace = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.trReplace"));
            Pattern td = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.td"));
            Pattern tdReplace = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.tdReplace"));
            Pattern href = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.href"));
            Pattern hrefReplace = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.hrefReplace"));
            Pattern aReplace = Pattern.compile(regexProps.getProperty("regex.areaAvaliacao.aReplace"));
            
            matcher = tab1.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = tab2.matcher(page);
            page = matcher.replaceAll("<");
            
            matcher = newline.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = divResultado.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = table.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tbody.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tr.matcher(page);
            String nome;
            
            while (matcher.find()) {
                
                page = matcher.group();
                
                matcher2 = trReplace.matcher(page);
                page = matcher2.replaceAll("");
                
                matcher2 = td.matcher(page);
                matcher2.find();
                page = matcher2.group();
                
                matcher2 = tdReplace.matcher(page);
                page = matcher2.replaceAll("");
                String a = page;
                
                matcher2 = href.matcher(page);
                matcher2.find();
                page = matcher2.group();
                
                matcher2 = hrefReplace.matcher(page);
                page = matcher2.replaceAll("");
                
                if (page.split(regexProps.getProperty("regex.areaAvaliacao.hrefSplit"))[1].equals(codigo)) {
                    matcher2 = aReplace.matcher(a);
                    nome = matcher2.replaceAll("");
                    AreaAvaliacao aa = new AreaAvaliacao();
                    aa.setCodigo(codigo);
                    aa.setNome(nome);
                    aar.save(aa);
                    log.info(aa.getCodigo() + " " + aa.getNome());
                    buscarPorAreaConhecimento(aar, acr, ir);
                    break;
                }
                
            }
            
        } catch (MalformedURLException mue) {
            
            log.info(mue.getMessage());
            
        } catch (Exception e) {
            
            log.info(e.getMessage());
            
        }
        
    }
    
    public static void buscarPorAreaConhecimento(AreaAvaliacaoRepository aar, AreaConhecimentoRepository acr,
        InstituicaoRepository ir) throws IOException {
        
        log.info("Buscando as informações da Área de Conhecimento e salvando no Banco de Dados.");
        Properties urlProps = getProps("src/main/resources/url.properties");
        Properties regexProps = getProps("src/main/resources/regex.properties");
        
        AreaAvaliacao aa = aar.findByCodigo(urlProps.getProperty("url.codigo"));
        String sucupira = urlProps.getProperty("url.sucupira");
        String areaConhecimento = urlProps.getProperty("url.areaConhecimento");
        
        try {
            
            URL url = new URL(sucupira + areaConhecimento + aa.getCodigo());
            String page = getPage(url);
            
            Matcher matcher, matcher2;
            
            Pattern tab1 = Pattern.compile(regexProps.getProperty("regex.tab1"));
            Pattern tab2 = Pattern.compile(regexProps.getProperty("regex.tab2"));
            Pattern newline = Pattern.compile(regexProps.getProperty("regex.newline"));
            Pattern divResultado = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.divResultado"));
            Pattern table = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.table"));
            Pattern tbody = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.tbody"));
            Pattern tr = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.tr"));
            Pattern trReplace = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.trReplace"));
            Pattern td = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.td"));
            Pattern tdReplace = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.tdReplace"));
            Pattern href = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.href"));
            Pattern hrefReplace = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.hrefReplace"));
            Pattern aReplace = Pattern.compile(regexProps.getProperty("regex.areaConhecimento.aReplace"));
            
            matcher = tab1.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = tab2.matcher(page);
            page = matcher.replaceAll("<");
            
            matcher = newline.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = divResultado.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = table.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tbody.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tr.matcher(page);
            String nome, codigo;
            
            while (matcher.find()) {
                
                page = matcher.group();
                
                matcher2 = trReplace.matcher(page);
                page = matcher2.replaceAll("");
                
                matcher2 = td.matcher(page);
                
                if (matcher2.find()) {
                    
                    page = matcher2.group();

                    matcher2 = tdReplace.matcher(page);
                    page = matcher2.replaceAll("");
                    String a = page;

                    matcher2 = href.matcher(page);
                    matcher2.find();
                    page = matcher2.group();

                    matcher2 = hrefReplace.matcher(page);
                    page = matcher2.replaceAll("");
                    codigo = page.split(regexProps.getProperty("regex.areaConhecimento.hrefSplit"))[1];
                    
                    matcher2 = aReplace.matcher(a);
                    nome = matcher2.replaceAll("");
                    
                    AreaConhecimento ac = new AreaConhecimento();
                    ac.setNome(nome);
                    ac.setCodigo(codigo);
                    ac.setAreaAvaliacao(aa);
                    acr.save(ac);
                    
                    log.info(ac.getCodigo() + " " + ac.getNome());
                    buscarPorInstituicao(ac.getCodigo(), acr, ir);
                
                }
                
            }
            
        } catch (MalformedURLException mue) {
            
            log.info(mue.getMessage());
            
        } catch (Exception e) {
            
            log.info(e.getMessage());
            
        }
        
    }
    
    public static void buscarPorInstituicao(String codigo, AreaConhecimentoRepository acr,
        InstituicaoRepository ir) throws IOException {
        
        log.info("Buscando as informações da Instituição de Ensino e salvando no Banco de Dados.");
        Properties urlProps = getProps("src/main/resources/url.properties");
        Properties regexProps = getProps("src/main/resources/regex.properties");
        
        AreaConhecimento ac = acr.findByCodigo(codigo);
        String sucupira = urlProps.getProperty("url.sucupira");
        String instituicao = urlProps.getProperty("url.instituicao");
        String[] aux = instituicao.split("[*]");
        instituicao = aux[0] + ac.getAreaAvaliacao().getCodigo() + aux[1] + ac.getCodigo();
        
        try {
            
            URL url = new URL(sucupira + instituicao);
            String page = getPage(url);
            
            Matcher matcher, matcher2;
            
            Pattern tab1 = Pattern.compile(regexProps.getProperty("regex.tab1"));
            Pattern tab2 = Pattern.compile(regexProps.getProperty("regex.tab2"));
            Pattern newline = Pattern.compile(regexProps.getProperty("regex.newline"));
            Pattern divResultado = Pattern.compile(regexProps.getProperty("regex.instituicao.divResultado"));
            Pattern table = Pattern.compile(regexProps.getProperty("regex.instituicao.table"));
            Pattern tbody = Pattern.compile(regexProps.getProperty("regex.instituicao.tbody"));
            Pattern tr = Pattern.compile(regexProps.getProperty("regex.instituicao.tr"));
            Pattern trReplace = Pattern.compile(regexProps.getProperty("regex.instituicao.trReplace"));
            Pattern td = Pattern.compile(regexProps.getProperty("regex.instituicao.td"));
            Pattern tdReplace = Pattern.compile(regexProps.getProperty("regex.instituicao.tdReplace"));
            Pattern href = Pattern.compile(regexProps.getProperty("regex.instituicao.href"));
            Pattern hrefReplace = Pattern.compile(regexProps.getProperty("regex.instituicao.hrefReplace"));
            Pattern aReplace = Pattern.compile(regexProps.getProperty("regex.instituicao.aReplace"));
            
            matcher = tab1.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = tab2.matcher(page);
            page = matcher.replaceAll("<");
            
            matcher = newline.matcher(page);
            page = matcher.replaceAll("");
            
            matcher = divResultado.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = table.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tbody.matcher(page);
            matcher.find();
            page = matcher.group();
            
            matcher = tr.matcher(page);
            String nome, codigoInstituicao, acronimo;
            
            while (matcher.find()) {
                
                page = matcher.group();
                
                matcher2 = trReplace.matcher(page);
                page = matcher2.replaceAll("");
                
                matcher2 = td.matcher(page);
                
                if (matcher2.find()) {
                    
                    page = matcher2.group();

                    matcher2 = tdReplace.matcher(page);
                    page = matcher2.replaceAll("");
                    String a = page;

                    matcher2 = href.matcher(page);
                    matcher2.find();
                    page = matcher2.group();

                    matcher2 = hrefReplace.matcher(page);
                    page = matcher2.replaceAll("");
                    codigoInstituicao = page.split(regexProps.getProperty("regex.instituicao.hrefSplit"))[1];
                    
                    matcher2 = aReplace.matcher(a);
                    String[] aContent = matcher2.replaceAll("").split("( [(])");
                    nome = aContent[0];
                    acronimo = aContent[1].replaceAll("[)]", "");
                    
                    Instituicao i = new Instituicao();
                    i.setCodigo(codigoInstituicao);
                    i.setAcronimo(acronimo);
                    i.setNome(nome);
                    ir.save(i);
                    log.info(i.getCodigo() + " " + i.getAcronimo() + " " + i.getNome());
                    
                }
                
            }
            
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
            buscarPorAreaAvaliacao(aar, acr, ir);
            
        };
        
    }
    
}
