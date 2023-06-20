/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apiWK.Controller;

import com.apiWK.Entity.Doador;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.Mapping;
import com.apiWK.Repository.Doadores;

/**
 *
 * @author guilh
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DoadorController {

    private Doadores pessoasRepository;

    public DoadorController(Doadores pessoasRepository) {
        this.pessoasRepository = pessoasRepository;
    }

    @GetMapping("/contagem-por-estado")
    public List<Map<String, Object>> contarCandidatosPorEstado() {
        List<Object[]> result = pessoasRepository.contarCandidatosPorEstado();

        return result.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "faixa_idade", row[0],
                            "total_candidatos", row[1]
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/percentual-imc-faixa")
    public List<Map<String, Object>> calcularImcMedioPorFaixaIdade() {
        List<Object[]> result = pessoasRepository.calcularImcMedioPorFaixaIdade();

        return result.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "faixa_idade", row[0],
                            "total_candidatos", row[1]
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/percentual-obesos-sexo")
    public List<Map<String, Object>> calcularPercentualObesosPorSexo() {
        List<Object[]> result = pessoasRepository.calcularPercentualObesosPorSexo();

        return result.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "Sexo", row[0],
                            "Total", row[1],
                            "obesos", row[2],
                            "percentual_obesos", row[3]
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/media-idade-tipo-sanguineo")
    public List<Map<String, Object>> calcularMediaIdadePorTipoSanguineo() {
        List<Object[]> result = pessoasRepository.calcularMediaIdadePorTipoSanguineo();

        return result.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "Tipo", row[0],
                            "media", row[1]
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/doador-recptor")
    public List<Map<String, Object>> calcularQuantidadeDoadoresPorTipoReceptor() {
        List<Object[]> result = pessoasRepository.calcularQuantidadeDoadoresPorTipoReceptor();

        return result.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "Tipo", row[0],
                            "quantidade", row[1]
                    );
                    return map;
                })
                .collect(Collectors.toList());
    }

}
