package viagem.servicos;

import java.util.*;
import viagem.entidades.*;
import viagem.enums.*;

public final class ServicoIniciarDestinos {

    //Inicia destinos, hurb ou Milhas
    protected List<Destino> iniciarDestinos(SiglaEstado se, TipoServicoViagem tsv) {

        List<Destino> destinosEstado = (tsv.equals(TipoServicoViagem.HURB))
                ? new ArrayList<>(destinosHurb(se))
                : new ArrayList<>(destinosMilhas(se));

        return destinosEstado;
    }

    private List<Destino> destinosHurb(SiglaEstado se) {

        List<Cidade> cidades = new ArrayList<>();

        int idEstado = se.getId();

        //Verifica pelo id do estado quais destinos serão iniciados
        switch (idEstado) {

            //Rio de Janeiro
            case 1 -> {

                cidades.add(new Cidade("Macaé", se));
                cidades.add(new Cidade("Rio de Janeiro", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Macaé")) {

                        cidade.getLocais().add(new Local("Praia dos Cavaleiros"));
                        cidade.getLocais().add(new Local("Praia do Forte"));

                    }

                    if (cidade.getNome().equals("Rio de Janeiro")) {

                        cidade.getLocais().add(new Local("Barra da Tijuca"));
                        cidade.getLocais().add(new Local("Ipanema"));
                    }
                }
            }

            //SP
            case 2 -> {
                cidades.add(new Cidade("Mogi das Cruzes", se));
                cidades.add(new Cidade("Guarujá", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Mogi das Cruzes")) {

                        cidade.getLocais().add(new Local("Orquidário Oriental"));
                        cidade.getLocais().add(new Local("Casarão do Chá"));
                    }

                    if (cidade.getNome().equals("Guarujá")) {

                        cidade.getLocais().add(new Local("Acqua Mundo"));
                        cidade.getLocais().add(new Local("Mirante do Morro"));
                    }

                }
            }

            //BSB
            case 3 -> {

                cidades.add(new Cidade("Aguas Claras", se));
                cidades.add(new Cidade("Asa Norte", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Aguas Claras")) {

                        cidade.getLocais().add(new Local("Parque Ecológico"));
                        cidade.getLocais().add(new Local("Coco Bambu"));
                    }

                    if (cidade.getNome().equals("Asa Norte")) {

                        cidade.getLocais().add(new Local("Memorial JK"));
                        cidade.getLocais().add(new Local("Parque Olho D'agua"));
                    }
                }
            }

            //BA
            case 4 -> {

                cidades.add(new Cidade("Salvador", se));
                cidades.add(new Cidade("Pelourinho", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Salvador")) {

                        cidade.getLocais().add(new Local("Basílica"));
                        cidade.getLocais().add(new Local("Museu Náutico"));
                    }

                    if (cidade.getNome().equals("Pelourinho")) {

                        cidade.getLocais().add(new Local("Largo"));
                        cidade.getLocais().add(new Local("Fundação Casa de Jorge"));
                    }

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

        int idEstado = se.getId();

        switch (idEstado) {

            //Rio de Janeiro
            case 1 -> {
                cidades.add(new Cidade("Niterói", se));
                cidades.add(new Cidade("Belford Roxo", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Niterói")) {

                        cidade.getLocais().add(new Local("Museu de Arte"));
                        cidade.getLocais().add(new Local("Fortaleza de Santa Cruz"));
                    }

                    if (cidade.getNome().equals("Belford Roxo")) {

                        cidade.getLocais().add(new Local("Barilandia"));
                        cidade.getLocais().add(new Local("Vale do Ipê"));
                    }

                }
            }

            //SP
            case 2 -> {
                cidades.add(new Cidade("Mogi Guaçu", se));
                cidades.add(new Cidade("Alphaville", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Mogi Guaçu")) {

                        cidade.getLocais().add(new Local("Igreja Imaculada"));
                        cidade.getLocais().add(new Local("Museu Hermínio Bueno"));
                    }

                    if (cidade.getNome().equals("Alphaville")) {

                        cidade.getLocais().add(new Local("Parque Municipal"));
                        cidade.getLocais().add(new Local("Shopping Tamboré"));
                    }

                }
            }

            //BSB
            case 3 -> {

                cidades.add(new Cidade("Asa Sul", se));
                cidades.add(new Cidade("Ceilândia", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Asa Sul")) {

                        cidade.getLocais().add(new Local("Museu da República"));
                        cidade.getLocais().add(new Local("Pontão do Lago Sul"));
                    }

                    if (cidade.getNome().equals("Ceilândia")) {

                        cidade.getLocais().add(new Local("Caixa D'agua"));
                        cidade.getLocais().add(new Local("Balneário"));
                    }

                }
            }

            //BA
            case 4 -> {

                cidades.add(new Cidade("Trancoso", se));
                cidades.add(new Cidade("Corumbau", se));

                for (Cidade cidade : cidades) {

                    if (cidade.getNome().equals("Trancoso")) {

                        cidade.getLocais().add(new Local("Praia dos Nativos"));
                        cidade.getLocais().add(new Local("Praia dos Coqueiros"));
                    }

                    if (cidade.getNome().equals("Corumbau")) {

                        cidade.getLocais().add(new Local("Praia dos Nativos"));
                        cidade.getLocais().add(new Local("Praia de Cumuruxatiba"));
                    }

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
