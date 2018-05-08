/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sucupirafilter.repository;

/**
 *
 * @author isaac
 */

import org.springframework.data.repository.CrudRepository;
import br.edu.ufabc.sucupirafilter.model.Instituicao;

public interface InstituicaoRepository extends CrudRepository<Instituicao, Long> {
    Instituicao findByCodigo(String codigo);
}
