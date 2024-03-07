package trabalhopraticografo;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class GrafoTest {

    private Grafo grafo;
    private Cidade cidadeA;
    private Cidade cidadeB;
    private Cidade cidadeC;

    @BeforeEach
    void setUp() {
        grafo = new Grafo();
        cidadeA = new Cidade("Cidade A");
        cidadeB = new Cidade("Cidade B");
        cidadeC = new Cidade("Cidade C");
    }

    @Test
    public void testExisteEstradaEntreCidades() {
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        assertTrue(grafo.existeEstradaEntreCidades(cidadeA, cidadeB));
        assertTrue(grafo.existeEstradaEntreCidades(cidadeB, cidadeA));
        assertFalse(grafo.existeEstradaEntreCidades(cidadeA, new Cidade("Cidade C")));
    }

    @Test
    public void testExisteEstradaDeQualquerParaQualquer() {
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);

        assertFalse(grafo.existeEstradaDeQualquerParaQualquer());

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        assertTrue(grafo.existeEstradaDeQualquerParaQualquer());
    }

    @Test
    public void testCidadesDiretamenteInacessiveis() {
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);

        List<Cidade> inacessiveis = grafo.cidadesDiretamenteInacessiveis(cidadeA);

        assertTrue(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        inacessiveis = grafo.cidadesDiretamenteInacessiveis(cidadeA);

        assertFalse(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));
    }

    @Test
    public void testCidadesCompletamenteInacessiveis() {
        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);

        List<Cidade> inacessiveis = grafo.cidadesCompletamenteInacessiveis(cidadeA);

        assertTrue(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));

        grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));

        inacessiveis = grafo.cidadesCompletamenteInacessiveis(cidadeA);

        assertFalse(inacessiveis.contains(cidadeB));
        assertTrue(inacessiveis.contains(cidadeC));
    }

    @Test
    public void testEncontrarCaminhoHamiltoniano() {

        grafo.adicionarCidade(cidadeA);
        grafo.adicionarCidade(cidadeB);
        grafo.adicionarCidade(cidadeC);

        Aresta aresta1 = new Aresta(cidadeA, cidadeB, 1);
        Aresta aresta2 = new Aresta(cidadeB, cidadeC, 1);
        Aresta aresta3 = new Aresta(cidadeC, cidadeA, 1);

        grafo.adicionarAresta(aresta1);
        grafo.adicionarAresta(aresta2);
        grafo.adicionarAresta(aresta3);

        List<Cidade> caminhoEsperado = Arrays.asList(cidadeA, cidadeC, cidadeB);
        List<Cidade> caminhoReal = grafo.encontrarCaminhoHamiltoniano(cidadeA);

        assertEquals(caminhoEsperado, caminhoReal);
    }
}

// @Test
// public void testRecomendarVisitaTodasCidades() {
// grafo.adicionarCidade(cidadeA);
// grafo.adicionarCidade(cidadeB);
// grafo.adicionarCidade(cidadeC);

// List<Cidade> caminhoMinimo = grafo.recomendarVisitaTodasCidades(cidadeA);

// // Implemente este teste com base na sua implementação do método
// // recomendarVisitaTodasCidades
// }

// @Test
// public void testRotaHamiltoniana() {
// grafo.adicionarCidade(cidadeA);
// grafo.adicionarCidade(cidadeB);
// grafo.adicionarCidade(cidadeC);
// grafo.adicionarAresta(new Aresta(cidadeA, cidadeB, 100));
// grafo.adicionarAresta(new Aresta(cidadeB, cidadeC, 200));

// List<Aresta> rota = grafo.rotaHamiltoniana(cidadeA.getNome());

// // Implemente este teste com base na sua implementação do método
// // rotaHamiltoniana
// }
