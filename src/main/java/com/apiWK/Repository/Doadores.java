/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.apiWK.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author guilh
 */
public interface Doadores extends JpaRepository<com.apiWK.Entity.Doador, Integer> {

    @Query("SELECT p.estado, COUNT(p) AS total_candidatos FROM Doador p GROUP BY p.estado")
    List<Object[]> contarCandidatosPorEstado();

    @Query(value = "SELECT FLOOR(DATEDIFF(CURRENT_DATE, STR_TO_DATE(data_nasc, '%d/%m/%Y')) / 365 / 10) * 10 AS faixa_idade, ROUND(AVG(peso / (altura * altura)), 2) AS imc_medio FROM Doador GROUP BY FLOOR(DATEDIFF(CURRENT_DATE, STR_TO_DATE(data_nasc, '%d/%m/%Y')) / 365 / 10) * 10 ORDER BY faixa_idade", nativeQuery = true)
    List<Object[]> calcularImcMedioPorFaixaIdade();

    @Query(value = "SELECT sexo, COUNT(*) AS total, "
            + "SUM(CASE WHEN peso / (altura * altura) > 30 THEN 1 ELSE 0 END) AS obesos, "
            + "ROUND((SUM(CASE WHEN peso / (altura * altura) > 30 THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS percentual_obesos "
            + "FROM doador "
            + "GROUP BY sexo", nativeQuery = true)
    List<Object[]> calcularPercentualObesosPorSexo();

    @Query(value = "SELECT tipo_sanguineo, AVG(DATEDIFF(CURRENT_DATE, STR_TO_DATE(data_nasc, '%d/%m/%Y')) / 365) AS media_idade "
            + "FROM doador "
            + "GROUP BY tipo_sanguineo",
            nativeQuery = true)
    List<Object[]> calcularMediaIdadePorTipoSanguineo();

    @Query(value = "SELECT receptor.tipo_sanguineo AS tipoReceptor, COUNT(DISTINCT doador.id) AS quantidadeDoadores "
            + "FROM doador AS receptor "
            + "INNER JOIN pessoa AS doador ON ("
            + "(receptor.tipo_sanguineo = 'A+' AND doador.tipo_sanguineo IN ('AB+', 'A+')) OR "
            + "(receptor.tipo_sanguineo = 'A-' AND doador.tipo_sanguineo IN ('A+', 'A-', 'AB+', 'AB-')) OR "
            + "(receptor.tipo_sanguineo = 'B+' AND doador.tipo_sanguineo IN ('B+', 'AB+')) OR "
            + "(receptor.tipo_sanguineo = 'B-' AND doador.tipo_sanguineo IN ('B+', 'B-', 'AB+', 'AB-')) OR "
            + "(receptor.tipo_sanguineo = 'AB+' AND doador.tipo_sanguineo = 'AB+') OR "
            + "(receptor.tipo_sanguineo = 'AB-' AND doador.tipo_sanguineo IN ('AB+', 'AB-')) OR "
            + "(receptor.tipo_sanguineo = 'O+' AND doador.tipo_sanguineo IN ('A+', 'B+', 'O+', 'AB+')) OR "
            + "(receptor.tipo_sanguineo = 'O-' AND doador.tipo_sanguineo IN ('A+', 'B+', 'O+', 'AB+', 'A-', 'B-', 'O-', 'AB')) "
            + ") "
            + "WHERE "
            + "(YEAR(CURDATE()) - YEAR(STR_TO_DATE(doador.data_nasc, '%d/%m/%Y'))) BETWEEN 16 AND 69 "
            + "AND doador.peso > 50 "
            + "GROUP BY receptor.tipo_sanguineo",
            nativeQuery = true)
    List<Object[]> calcularQuantidadeDoadoresPorTipoReceptor();

}
