package com.example.sortanimation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;

public class Principal extends Application {
    // tela inicial que e apresentada
    AnchorPane pane;
    // botao que inicia o metodo para iniciar a ordenacao dos elementos
    Button botao_inicio;
    // vetor que e exibido
    private Button vet[];
    private static int TAMVET = 16;
    private static int TEMPO = 10;
    private static int DISTANCIA = 45;
    private int vetInt[];
    private int TL;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        configura_botao_inicio();
        configura_pane();
        geraVetor();
        configura_exibicao_vetor();

        stage.setTitle("Bucket Sort e Tim Sort");
        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    // configuracoes de botoes e funcoes para insercao de elementos na tela a seguir:
    public void configura_botao_inicio() {
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setText("Iniciar ordenação");
        botao_inicio.setOnAction(e -> {
            move_botoes();
        });
    }

    public void add_pane(Button bnt) {
        pane.getChildren().add(bnt);
    }

    public void configura_pane() {
        pane = new AnchorPane();
        add_pane(botao_inicio);
    }

    // configuracoes de exibicao e geracao de vetor em forma de botoes a seguir:
    public boolean existe(int num) {
        int i = 0;
        while (i < TL && vetInt[i] != num)
            i++;
        return i != TL;
    }

    public int geraNumero(int min, int max) {
        Random random = new Random();
        int value;
        do {
            value = random.nextInt(min, max);
        } while (existe(value));
        return value;
    }

    public void geraVetor() {
        TL = 0;
        vetInt = new int[TAMVET];
        while (TL < TAMVET)
            vetInt[TL++] = geraNumero(1, 100);
    }

    public void configura_exibicao_vetor() {
        int x = 100, y = 200, tamAltura = 40, tamComprimento = 40, numFonte = 14;
        vet = new Button[TAMVET];
        for (int i = 0; i < TAMVET; i++) {
            vet[i] = new Button(vetInt[i] + "");
            vet[i].setLayoutX(x);
            vet[i].setLayoutY(y);
            vet[i].setMinHeight(tamAltura);
            vet[i].setMinWidth(tamComprimento);
            vet[i].setFont(new Font(numFonte));
            add_pane(vet[i]);

            x += DISTANCIA;
        }
    }

    // configuracoes e funcoes da animacao a seguir:
    public void intervalo() {
        try {
            Thread.sleep(TEMPO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void troca_botoes(int pos1, int pos2) {
        double posInicial1 = vet[pos1].getLayoutX(), posInicial2 = vet[pos2].getLayoutX();
        int distPerc = Math.abs((int) (posInicial2 - posInicial1) / 5);
        for (int i = 0; i < 10; i++) {
            Platform.runLater(() -> vet[pos1].setLayoutY(vet[pos1].getLayoutY() + 5));
            Platform.runLater(() -> vet[pos2].setLayoutY(vet[pos2].getLayoutY() - 5));
            intervalo();
        }
        if (pos1 < pos2) {
            for (int i = 0; i < distPerc; i++) {
                Platform.runLater(() -> vet[pos1].setLayoutX(vet[pos1].getLayoutX() + 5));
                Platform.runLater(() -> vet[pos2].setLayoutX(vet[pos2].getLayoutX() - 5));
                intervalo();
            }
        } else {
            for (int i = 0; i < distPerc; i++) {
                Platform.runLater(() -> vet[pos1].setLayoutX(vet[pos1].getLayoutX() - 5));
                Platform.runLater(() -> vet[pos2].setLayoutX(vet[pos2].getLayoutX() + 5));
                intervalo();
            }
        }
        for (int i = 0; i < 10; i++) {
            Platform.runLater(() -> vet[pos1].setLayoutY(vet[pos1].getLayoutY() - 5));
            Platform.runLater(() -> vet[pos2].setLayoutY(vet[pos2].getLayoutY() + 5));
            intervalo();
        }

        Button aux = vet[pos1];
        vet[pos1] = vet[pos2];
        vet[pos2] = aux;
    }

    // move botoes do array para cima/baixo/esquerda/direta de acordo com o intervalo determinado
    public void move_para_cima(int inicio, int fim) {
        for (int j = 0; j < 10; j++) {
            for (int i = inicio; i <= fim; i++) {
                int finalI = i;
                Platform.runLater(() -> vet[finalI].setLayoutY(vet[finalI].getLayoutY() - 5));
            }
            intervalo();
        }
    }

    public void move_para_baixo(int inicio, int fim) {
        for (int j = 0; j < 10; j++) {
            for (int i = inicio; i <= fim; i++) {
                int finalI = i;
                Platform.runLater(() -> vet[finalI].setLayoutY(vet[finalI].getLayoutY() + 5));
            }
            intervalo();
        }
    }

    public void move_para_esquerda(int inicio, int fim) {
        for (int j = 0; j < 9; j++) {
            for (int i = inicio; i <= fim; i++) {
                int finalI = i;
                Platform.runLater(() -> vet[finalI].setLayoutX(vet[finalI].getLayoutX() - 5));
            }
            intervalo();
        }
    }

    public void move_para_direita(int inicio, int fim) {
        for (int j = 0; j < 9; j++) {
            for (int i = inicio; i <= fim; i++) {
                int finalI = i;
                Platform.runLater(() -> vet[finalI].setLayoutX(vet[finalI].getLayoutX() + 5));
            }
            intervalo();
        }
    }

    // posiciona o botao em uma posicao principal do vetor oficial
    public void posiciona_botao(int posAtual, int posDirecao) {
        int y = 200, x = 100;
        while (vet[posAtual].getLayoutX() > x + DISTANCIA * posDirecao) {

        }
    }

    // funcoes de ordenacao Tim Sort e Bucket Sort a seguir:
    public void move_botoes() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                timSort(4);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void timSort(int runs) {
        int n = TAMVET / runs;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++)
                move_para_baixo(i * n, i * n + n - 1);
        }
        for (int i = 0; i < TAMVET; i += n) {
            insertionSort(i, Math.min(i + n - 1, TAMVET - 1));
        }



        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++)
                move_para_cima(i * n, i * n + n - 1);
        }
    }

    public void insertionSort(int inicio, int fim) {
        int j, temp;
        for (int i = inicio + 1; i <= fim; i++) {
            j = i - 1;
            while (j >= inicio && vetInt[j] > vetInt[j + 1]) {
                temp = vetInt[j + 1];
                vetInt[j + 1] = vetInt[j];
                vetInt[j] = temp;
                troca_botoes(j, j + 1);
                j--;
            }
        }
    }


}