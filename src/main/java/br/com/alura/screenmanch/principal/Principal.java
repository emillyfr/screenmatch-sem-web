package br.com.alura.screenmanch.principal;

import br.com.alura.screenmanch.model.DadosEpisodio;
import br.com.alura.screenmanch.model.DadosSerie;
import br.com.alura.screenmanch.model.DadosTemporadas;
import br.com.alura.screenmanch.model.Episodio;
import br.com.alura.screenmanch.service.ConsumoApi;
import br.com.alura.screenmanch.service.ConverteDados;
import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.sun.source.util.SourcePositions;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private ConsumoApi consumo = new ConsumoApi();
    private final String API_KEY = "&apikey=fda65959";
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSerie = new ArrayList<>();

    public void exibeMenu () {
        var opcao = -1;
        while(opcao != 0){
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries 
                    0- Sair
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscaSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo ...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }



        private void buscaSerieWeb(){
            DadosSerie dados = getDadosSerie();
            dadosSerie.add(dados);
            System.out.println(dados);
        }

        private DadosSerie getDadosSerie() {
            System.out.println(" Digite o nome da série para a busca: ");
            var nomeSerie = leitura.nextLine();
            nomeSerie = nomeSerie.replaceAll("\\s+"," ").replace(" ", "+");
            var json = consumo.obterDados(ENDERECO+nomeSerie+API_KEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }

        private void buscarEpisodioPorSerie() {
            DadosSerie dadosSerie = getDadosSerie();
            List<DadosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= dadosSerie.totalTemp(); i++) {
                var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+"));
                DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
                temporadas.add(dadosTemporadas);
            }
            temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas(){
        dadosSerie.forEach(System.out::println);
    }
}









////        for(int i = 0; i< dados.totalTemp(); i++){
////            List<DadosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
////            for(int j = 0; j< episodiosTemporadas.size();j++){
////                System.out.println(episodiosTemporadas.get(j).titulo());
////            }
////        }
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());
//
//        System.out.println("Top 5 episodios");
//        dadosEpisodios.stream()
//                .filter(e-> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                .map(d -> new Episodio(t.numero(), d))
//                ).collect(Collectors.toList());
//        episodios.forEach(System.out::println);
//
////        System.out.println("Digite um trecho do titulo: ");
////        var trechoTitulo = leitura.nextLine();
////        Optional<Episodio> episodioBuscado =  episodios.stream()
////                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
////                .findFirst();
////        if( episodioBuscado.isPresent()){
////            System.out.println("Episodio encontrado!");
////            System.out.println("Temporada: "+episodioBuscado.get().getTemporada());
////        }else{
////            System.out.println("Episodio não encontrado!");
////        }
//
////        System.out.println("A partir de que ano você deseja ver os episodios? ");
////        var ano = leitura.nextInt();
////        leitura.nextLine();
////        LocalDate dataBusca = LocalDate.of(ano,1,1);
////        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        episodios.stream()
////                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
////                .forEach(e -> System.out.println(
////                        "Temporada: "+ e.getTemporada()+
////                                " Episodio: "+e.getNumeroEpisodio()+
////                                " Data lançamento: "+e.getDataLancamento().format(formatador)
////                ));
//
//        Map<Integer,Double> avaliacoesPorTemporada = episodios.stream()
//                .filter(e -> e.getAvaliacao()>0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avaliacoesPorTemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao()>0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println("Média: "+est.getAverage());
//        System.out.println("Mínimo: "+est.getMax()+" e Maximo: "+est.getMin());
//        System.out.println("Quantidade de episodios avaliados: "+est.getCount());
//    }
//

