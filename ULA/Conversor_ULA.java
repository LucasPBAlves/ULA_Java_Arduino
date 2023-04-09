

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Conversor_ULA {
    @SuppressWarnings("StringConcatenationInLoop")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Pede o nome do arquivo
        System.out.print("Insira o nome do arquivo: ");
        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scanner.nextLine()), StandardCharsets.UTF_8))) {
            Scanner stream = new Scanner(reader);

            //Pega o conteúdo do arquivo
            String content = "";
            //O conteúdo gerado
            String output = "";
            char x = '*';
            char y = '*';
            char w;

            do {
                content += stream.nextLine();
            } while (stream.hasNextLine());

            //Pega o conteúdo entre "inicio:" e "fim."
            String input = content.substring(content.indexOf("inicio:") + 7, content.indexOf("fim."));

            //Percorre o conteúdo
            for (String _line : input.split(";")) {
                String line = _line.trim();
                if (line.isBlank()) continue;
                String substring = line.substring(line.indexOf('=') + 1).trim();
                switch (line.charAt(0)) {
                    case 'X' -> x = intToHex(Integer.parseInt(substring));
                    case 'Y' -> y = intToHex(Integer.parseInt(substring));
                    case 'W' -> {
                        w = getWHex(substring);
                        if (x == '*' || y == '*') {
                            throw new IllegalStateException("X e Y devem ter um valor definido antes de definir o valor de W");
                        }

                        //Adiciona o valor gerado ao output
                        output += String.format("%s%s%s ", x, y, w);
                    }
                    default -> throw new IllegalStateException("Character illegal '" + line.charAt(0) + "'!");
                }
            }

            //Imprime o output
            System.out.println(output.toUpperCase().trim());

            //Pergunta se o usuário deseja salvar o arquivo
            System.out.print("Deseja salvar o arquivo? (s/n) ");
            //Se sim, pede o nome do arquivo e salva
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Insira o nome do arquivo: ");
                String fileName = scanner.nextLine();
                //Salva o arquivo
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                    //Escreve o output no arquivo, em maiúsculo e sem espaços no final e no começo
                    writer.write(output.toUpperCase().trim());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Função que transforma número para HEX
    public static char intToHex(int valor) {
        return Integer.toHexString(valor).charAt(0);
    }

    //Transforma o nome mnemônico em HEX
    public static char getWHex(String mnenomico) {
        return switch (mnenomico) {
            case "An" -> '0';
            case "nAoB" -> '1';
            case "AneB" -> '2';
            case "zeroL" -> '3';
            case "nAeB" -> '4';
            case "Bn" -> '5';
            case "AxB" -> '6';
            case "AeBn" -> '7';
            case "AnoB" -> '8';
            case "nAxB" -> '9';
            case "copiaB" -> 'A';
            case "AeB" -> 'B';
            case "umL" -> 'C';
            case "AoBn" -> 'D';
            case "AoB" -> 'E';
            case "copiaA" -> 'F';
            default -> throw new IllegalStateException("Valor invalido: " + mnenomico);
        };
    }
}