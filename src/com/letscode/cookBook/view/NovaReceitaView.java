package com.letscode.cookBook.view;

import com.letscode.cookBook.controller.Catalogo;
import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class NovaReceitaView {
    Scanner scanner;
    Receita receita;
    String nome;
    Categoria categoria;
    int tempoPreparo;
    Rendimento rendimento;
    List<Ingrediente> ingredientes;
    List<String> modoPreparo;

    public NovaReceitaView() {
        this.ingredientes = new ArrayList<Ingrediente>();
        this.modoPreparo = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }


    public void addNewReceita(Catalogo catalogo)  {
        receita = new Receita();
        receita.setNome(askNome());
        receita.setCategoria(askCategoria());
        receita.setTempoPreparo(askTempoDePreparo());
        receita.setRendimento(askRendimento());
        receita.setIngredientes(askIngredientes());
        receita.setModoPreparo(askModoPreparo());

       catalogo.add(receita);

    }

    private String askNome() {
        System.out.println("Qual o nome da receita?");
        nome = scanner.nextLine();
        if (nome.isBlank()) {
            System.out.println("Nome inválido!");
            askNome();
        }
        return nome;
    }

    private Categoria askCategoria()  {

        int index = IndexCategoria();

        categoria = Categoria.values()[index];


        return categoria;
    }

    private int IndexCategoria(){
        boolean valid = false;
        while(valid == false){
            try{
                System.out.println("Qual a categoria da receita?");
                for (Categoria cat : Categoria.values()) {
                    System.out.printf("%d - %s \n", cat.ordinal(), cat.name());
                }
                int index = Integer.parseInt(scanner.next());
                if(index >=0){
                    valid = true;
                }
                if (index < 0 || index >= Categoria.values().length) {
                    System.out.println("Categoria inválida!");
                    index = IndexCategoria();
                }
                return index;
            }catch (Exception e){
                System.out.println("Digite um número válido.");

            }
        }

        return 0;

    }

    private int askTempoDePreparo() {
        boolean valid = false;
        while(!valid){
            try{
                System.out.println("Qual o tempo de preparo?");
                tempoPreparo = Integer.parseInt(scanner.next());
                if(tempoPreparo >= 0){
                    valid = true;
                }else{
                    System.out.println("Não existe tempo negativo. Digite um valor válido!");
                }

            }catch(Exception e){
                System.out.println("Digite um tempo válido!");
            }
        }


        return tempoPreparo;

    }

    private Rendimento askRendimento() {
        boolean valid = false;
        int quantidade = -1;
        while(!valid){
            try{
                System.out.println("Qual o rendimento (número inteiro)?");
                quantidade = Integer.parseInt(scanner.next());
                if(quantidade > 0){
                    valid = true;
                }
            }catch(Exception e){
                System.out.println("Por favor digite um número");
            }
        }

        int tipo = indexTipoRendimento();
        TipoRendimento tipoRendimento = TipoRendimento.values()[tipo];

        rendimento = new Rendimento(quantidade, tipoRendimento);
        return rendimento;
    }

    private int indexTipoRendimento(){
        boolean valid = false;
        int tipo = -1;
        while(!valid){
            try{
                System.out.println("Escolha a unidade de rendimento(número):");
                System.out.println("0 - Unidades ");
                System.out.println("1 - Copos");
                System.out.println("2 - Porções");
                tipo = Integer.parseInt(scanner.next());
                if(tipo >= 0){
                    valid = true;
                }
                if(tipo < 0 || tipo >= TipoRendimento.values().length){

                    System.out.println("Tipo de rendimento inválido");
                    tipo = indexTipoRendimento();

                }


            }catch(Exception e){
                System.out.println("Digite um número!");
            }

        }

        return tipo;

    }

    private int askTipoMedida(){
        boolean valid = false;
        int medida = -1;
        while(!valid){
            try{
                System.out.println("Tipo de medida ingrediente:");
                System.out.println("0 - Kilos ");
                System.out.println("1 - Gramas");
                System.out.println("2 - Litros");
                System.out.println("3 - Unidades ");
                System.out.println("4 - Mililitros");
                System.out.println("5 - Chicara");
                System.out.println("6 - Copo ");
                System.out.println("7 - Colher de Sopa");
                System.out.println("8 - Colher de Cha");
                System.out.println("9 - Colher de Café ");
                System.out.println("10 - Gotas");
                System.out.println("11 - Unidades");
                medida = Integer.parseInt(scanner.next());

                if(medida < 1 || medida > TipoMedida.values().length){
                    System.out.println("Tipo de medida inválida!");
                    medida = askTipoMedida();
                }
                if(medida >0){
                    valid = true;
                }
            }catch (Exception e){
                System.out.println("Digite um número!");
            }

        }


        return medida;
    }

    private Ingrediente[] askIngredientes(){

        boolean terminar = false;
        int quantidade = -1;

        System.out.println("Escreva os ingredientes utilizados:");

        do{
            boolean valid = false;
            System.out.println("Nome do ingrediente:");
            String nomeIngrediente = scanner.next();

            while(!valid){
                try{
                    System.out.println("Quantidade do ingrediente:");
                    quantidade = Integer.parseInt(scanner.next());
                    if(quantidade > 0){
                        valid = true;
                    }

                }catch (Exception e){
                    System.out.println("Digite um número!");
                }
            }

            int medida = askTipoMedida();

            TipoMedida tipoMedida = TipoMedida.values()[medida -1];

            Ingrediente novoIngrediente = new Ingrediente(nomeIngrediente,quantidade,tipoMedida);

            ingredientes.add(novoIngrediente);

            System.out.println("Gostaria de adicionar mais algum ingrediente? (s/n)");

            String resposta = scanner.next().toLowerCase();

            if(resposta.equals("n")){
                terminar = true;
            }


        }while(terminar == false);

        return ingredientes.toArray(Ingrediente[]::new);

    }

    private String[] askModoPreparo(){
        boolean terminar = false;
        System.out.println("Escreva o passo a passo de preparo:");
        do{

            System.out.println("Passo:");
            String passo = scanner.next();
            modoPreparo.add(passo);
            System.out.println("Gostaria de adicionar mais um passo? (s/n)");

            String resposta = scanner.next().toLowerCase();

            if(resposta.equals("n")){
                terminar = true;
            }


        }while(terminar == false);

        return modoPreparo.toArray(String[]::new);
    }



}
