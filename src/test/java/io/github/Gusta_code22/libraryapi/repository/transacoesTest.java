package io.github.Gusta_code22.libraryapi.repository;

import io.github.Gusta_code22.libraryapi.serice.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class transacoesTest {

    @Autowired
    TransacaoService transacaoService;


    /**
     * Commit -> confirmar as alteracoes
     * Rollback -> desfazer as alteracoes
     */
    @Test
    void transacaoSimples(){
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManeged(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
