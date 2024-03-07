package trabalhopraticografo;

import java.util.*;

public class Grafo {

    List<Cidade> cidades;
    List<Aresta> arestas;

    public Grafo() {
        cidades = new ArrayList<>();
        arestas = new ArrayList<>();
    }

    public void adicionarCidade(Cidade cidade) {
        if (cidade == null) {
            throw new IllegalArgumentException("Cidade não pode ser nula");
        }
        cidades.add(cidade);
    }

    public void adicionarAresta(Aresta aresta) {
        if (aresta == null) {
            throw new IllegalArgumentException("Aresta não pode ser nula");
        }
        arestas.add(aresta);
        aresta.getOrigem().adicionarVizinho(aresta.getDestino(), aresta.getPeso());
        aresta.getDestino().adicionarVizinho(aresta.getOrigem(), aresta.getPeso());
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    // Requisito (a): Verificar se existe estrada de qualquer cidade para qualquer
    public boolean existeEstradaEntreCidades(Cidade origem, Cidade destino) {
        if (origem != null && destino != null) {
            return origem.getVizinhos().containsKey(destino);
        } else {
            return false;
        }
    }

    public boolean existeEstradaDeQualquerParaQualquer() {
        List<Cidade> cidades = getCidades();

        for (Cidade cidadeOrigem : cidades) {
            for (Cidade cidadeDestino : cidades) {
                if (cidadeOrigem != cidadeDestino && !existeEstradaEntreCidades(cidadeOrigem, cidadeDestino)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Listar todas as cidades DIRETAMENTE inacessíveis a partir de uma cidade sede
    public List<Cidade> cidadesDiretamenteInacessiveis(Cidade cidadeSede) {

        List<Cidade> inacessiveis = new ArrayList<>();

        if (cidadeSede == null) {
            return inacessiveis;
        }

        Set<Cidade> acessiveis = new HashSet<>();

        for (Cidade cidade : cidadeSede.getVizinhos().keySet()) {
            acessiveis.add(cidade);
        }

        for (Cidade cidade : cidades) {
            if (!cidade.equals(cidadeSede) && !acessiveis.contains(cidade)) {
                inacessiveis.add(cidade);
            }
        }

        return inacessiveis;
    }

    // Requisito (b): Listar todas as cidades inacessíveis a partir de uma cidade
    // sede
    public List<Cidade> cidadesCompletamenteInacessiveis(Cidade cidadeSede) {
        List<Cidade> inacessiveis = new ArrayList<>();

        if (cidadeSede == null) {
            return inacessiveis;
        }

        Set<Cidade> acessiveis = new HashSet<>();
        Queue<Cidade> fila = new LinkedList<>();

        acessiveis.add(cidadeSede);
        fila.add(cidadeSede);

        while (!fila.isEmpty()) {
            Cidade atual = fila.poll();

            for (Cidade vizinho : atual.getVizinhos().keySet()) {
                if (!acessiveis.contains(vizinho)) {
                    acessiveis.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }

        for (Cidade cidade : cidades) {
            if (!acessiveis.contains(cidade)) {
                inacessiveis.add(cidade);
            }
        }

        return inacessiveis;
    }

    // Requisito (c): Recomendar visitação em todas as cidades e estradas.

    public void visitarTodas(String nomeCidadeInicial) {
        Cidade cidadeInicial = null;
        for (Cidade cidade : cidades) {
            if (cidade.getNome().equals(nomeCidadeInicial)) {
                cidadeInicial = cidade;
                break;
            }
        }
        if (cidadeInicial != null) {
            cidadeInicial.visitarTodas(new HashSet<>());
        } else {
            System.out.println("Cidade não encontrada: " + nomeCidadeInicial);
        }
    }

    // Requisito (d): Recomendar uma rota para um passageiro que deseja visitar
    // todas as cidades.

    public List<Cidade> encontrarCaminhoHamiltoniano(Cidade inicio) {
        List<Cidade> caminho = new ArrayList<>();
        if (encontrarCaminhoHamiltoniano(inicio, caminho, getCidades().size())) {
            return caminho;
        } else {
            System.out.println("Nenhum caminho hamiltoniano encontrado.");
            return null;
        }
    }

    private boolean encontrarCaminhoHamiltoniano(Cidade cidadeAtual, List<Cidade> caminho, int totalCidades) {
        caminho.add(cidadeAtual);

        if (caminho.size() == totalCidades) {
            return true;
        }

        for (Cidade vizinho : cidadeAtual.getVizinhos().keySet()) {
            if (!caminho.contains(vizinho)) {
                if (encontrarCaminhoHamiltoniano(vizinho, caminho, totalCidades)) {
                    return true;
                }
            }
        }

        // Poda: se nenhum caminho hamiltoniano pôde ser encontrado a partir desta
        // cidade,
        // remova-a do caminho e retorne false.
        caminho.remove(cidadeAtual);
        return false;
    }

    public int calcularPesoCaminho(List<Cidade> caminho) {
        int pesoTotal = 0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            Cidade cidadeAtual = caminho.get(i);
            Cidade proximaCidade = caminho.get(i + 1);
            pesoTotal += cidadeAtual.getVizinhos().get(proximaCidade);
        }
        return pesoTotal;
    }

    //ALTERNATIVO

    public List<Cidade> encontrarCaminhoHamiltonianoAproximado(Cidade inicio) {
        List<Cidade> caminho = new ArrayList<>();
        Set<Cidade> visitadas = new HashSet<>();
        visitadas.add(inicio);
        dfs(inicio, inicio, visitadas, caminho, getCidades().size());
        return caminho;
    }
    
    private void dfs(Cidade atual, Cidade inicio, Set<Cidade> visitadas, List<Cidade> caminho, int totalCidades) {
        caminho.add(atual);
    
        if (caminho.size() == totalCidades) {
            // Se todas as cidades foram visitadas, retorne ao início para completar o ciclo
            caminho.add(inicio);
            return;
        }
    
        for (Cidade vizinho : atual.getVizinhos().keySet()) {
            if (!visitadas.contains(vizinho)) {
                visitadas.add(vizinho);
                dfs(vizinho, inicio, visitadas, caminho, totalCidades);
                if (caminho.size() == totalCidades + 1) { // Se todas as cidades foram visitadas
                    return;
                }
                // Se não encontramos um caminho válido a partir deste vizinho,
                // remova-o do conjunto de cidades visitadas e do caminho atual
                visitadas.remove(vizinho);
                caminho.remove(caminho.size() - 1);
            }
        }
    }
    

    // public List<Aresta> rotaHamiltoniana(String nomeCidadeInicial) {
    //     List<Aresta> rota = new ArrayList<>();
    //     Set<Cidade> visitadas = new HashSet<>();
    //     Cidade atual = null;

    //     // Procurar a cidade inicial na lista de cidades
    //     for (Cidade cidade : getCidades()) {
    //         if (cidade.getNome().equals(nomeCidadeInicial)) {
    //             atual = cidade;
    //             break;
    //         }
    //     }

    //     while (atual != null) {
    //         visitadas.add(atual);

    //         Cidade proxima = null;
    //         int menorDistancia = Integer.MAX_VALUE;

    //         for (Map.Entry<Cidade, Integer> entry : atual.getVizinhos().entrySet()) {
    //             if (!visitadas.contains(entry.getKey()) && entry.getValue() < menorDistancia) {
    //                 proxima = entry.getKey();
    //                 menorDistancia = entry.getValue();
    //             }
    //         }

    //         if (proxima != null) {
    //             rota.add(new Aresta(atual, proxima, menorDistancia));
    //         }

    //         atual = proxima;
    //     }

    //     return rota;
    // }

    public Cidade buscarCidadePorNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome da cidade não pode ser nulo.");
        }

        for (Cidade cidade : cidades) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null; // Retorna null se a cidade não for encontrada
    }
}