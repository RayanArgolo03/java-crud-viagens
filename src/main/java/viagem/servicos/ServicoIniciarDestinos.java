package viagem.servicos;

import java.util.ArrayList;
import java.util.List;
import viagem.entidades.Cidade;
import viagem.entidades.Destino;
import viagem.entidades.Local;
import viagem.enums.SiglaEstado;
import viagem.enums.TipoServicoViagem;

public class ServicoIniciarDestinos {

    protected List<Destino> iniciarDestinos(SiglaEstado se, TipoServicoViagem tsv) {

        List<Destino> destinosEstado;

        if (tsv.equals(TipoServicoViagem.HURB)) {
            destinosEstado = new ArrayList<>(destinosHurb(se));
        } else {
            destinosEstado = new ArrayList<>(destinosMilhas(se));
        }

        return destinosEstado;
    }

    private List<Destino> destinosHurb(SiglaEstado se) {

        List<Cidade> cidades = new ArrayList<>();
        List<Local> locais = new ArrayList<>();

        int idEstado = se.getId();

        switch (idEstado) {

            //Rio de Janeiro
            case 1 -> {
                cidades.add(new Cidade("Macaé", se));
                cidades.add(new Cidade("Rio de Janeiro", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Macaé")) {
                        locais.add(new Local("Praia dos Cavaleiros"));
                        locais.add(new Local("Praia do Forte"));
                    }

                    if (cidade.getNome().equals("Rio de Janeiro")) {
                        locais.add(new Local("Barra da Tijuca"));
                        locais.add(new Local("Ipanema"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //SP
            case 2 -> {
                cidades.add(new Cidade("Mogi das Cruzes", se));
                cidades.add(new Cidade("Guarujá", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Mogi das Cruzes")) {
                        locais.add(new Local("Orquidário Oriental"));
                        locais.add(new Local("Casarão do Chá"));
                    }

                    if (cidade.getNome().equals("Guarujá")) {
                        locais.add(new Local("Acqua Mundo"));
                        locais.add(new Local("Mirante do Morro"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //BSB
            case 3 -> {

                cidades.add(new Cidade("Aguas Claras", se));
                cidades.add(new Cidade("Asa Norte", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Aguas Claras")) {
                        locais.add(new Local("Parque Ecológico"));
                        locais.add(new Local("Coco Bambu"));
                    }

                    if (cidade.getNome().equals("Asa Norte")) {
                        locais.add(new Local("Memorial JK"));
                        locais.add(new Local("Parque Olho D'agua"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //BA
            case 4 -> {

                cidades.add(new Cidade("Salvador", se));
                cidades.add(new Cidade("Pelourinho", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Salvador")) {
                        locais.add(new Local("Basílica"));
                        locais.add(new Local("Museu Náutico"));
                    }

                    if (cidade.getNome().equals("Pelourinho")) {
                        locais.add(new Local("Largo"));
                        locais.add(new Local("Fundação Casa de Jorge"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }
        }

        List<Destino> destinos = new ArrayList<>();
        for (Cidade c : cidades) {
            destinos.add(new Destino(c));
        }

        return destinos;
    }

    private List<Destino> destinosMilhas(SiglaEstado se) {

        List<Cidade> cidades = new ArrayList<>();
        List<Local> locais = new ArrayList<>();

        int idEstado = se.getId();

        switch (idEstado) {

            //Rio de Janeiro
            case 1 -> {
                cidades.add(new Cidade("Niterói", se));
                cidades.add(new Cidade("Belford Roxo", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Niterói")) {
                        locais.add(new Local("Museu de Arte"));
                        locais.add(new Local("Fortaleza de Santa Cruz"));
                    }

                    if (cidade.getNome().equals("Belford Roxo")) {
                        locais.add(new Local("Barilandia"));
                        locais.add(new Local("Vale do Ipê"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //SP
            case 2 -> {
                cidades.add(new Cidade("Mogi Guaçu", se));
                cidades.add(new Cidade("Alphaville", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Mogi Guaçu")) {
                        locais.add(new Local("Igreja Imaculada"));
                        locais.add(new Local("Museu Hermínio Bueno"));
                    }

                    if (cidade.getNome().equals("Alphaville")) {
                        locais.add(new Local("Parque Municipal"));
                        locais.add(new Local("Shopping Tamboré"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //BSB
            case 3 -> {

                cidades.add(new Cidade("Asa Sul", se));
                cidades.add(new Cidade("Ceilândia", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Asa Sul")) {
                        locais.add(new Local("Museu da República"));
                        locais.add(new Local("Pontão do Lago Sul"));
                    }

                    if (cidade.getNome().equals("Ceilândia")) {
                        locais.add(new Local("Caixa D'agua"));
                        locais.add(new Local("Balneário"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }

            //BA
            case 4 -> {

                cidades.add(new Cidade("Trancoso", se));
                cidades.add(new Cidade("Corumbau", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Trancoso")) {
                        locais.add(new Local("Praia dos Nativos"));
                        locais.add(new Local("Praia dos Coqueiros"));
                    }

                    if (cidade.getNome().equals("Corumbau")) {
                        locais.add(new Local("Praia de Corumbau"));
                        locais.add(new Local("Praia de Cumuruxatiba"));
                    }

                    cidade.getLocais().addAll(locais);
                    locais.clear();
                }
            }
        }
        List<Destino> destinos = new ArrayList<>();
        for (Cidade c : cidades) {
            destinos.add(new Destino(c));
        }

        return destinos;
    }
}
