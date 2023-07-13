package teste;

import java.util.Scanner;
import viagem.exceptions.GerenciadorException;
import viagem.servicos.GerenciadorViagens;
import viagem.servicos.GerenciarAcoes;
import viagem.servicos.ServicoIniciarDestinos;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            GerenciadorViagens gv = new GerenciadorViagens(new GerenciarAcoes(), new ServicoIniciarDestinos());

            int opcAcao = 0;
            do {
                
                System.out.println();
                gv.exibirOpcoesAcao();
                
                System.out.print("Sua escolha: ");
                opcAcao = sc.nextInt();

                while (opcAcao <= 0 || opcAcao > 4) {

                    System.out.println();
                    System.out.println("Opção inválida! (1 a 4)");

                    gv.exibirOpcoesAcao();
                    System.out.print("Sua escolha: ");

                    opcAcao = sc.nextInt();
                }

                gv.getGa().gerenciarAcoes(opcAcao);

            } while (opcAcao > 0 && opcAcao <= 4);
        }
        
        catch (GerenciadorException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
        
        catch (RuntimeException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
        
        finally {
            sc.close();
        }
    }

}
