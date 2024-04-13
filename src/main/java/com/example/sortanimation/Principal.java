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
        int x = 100, y = 200, tamAltura = 40, tamComprimento = 40, numFonte = 14, distancia = 45;
        vet = new Button[TAMVET];
        for (int i = 0; i < TAMVET; i++) {
            vet[i] = new Button(vetInt[i] + "");
            vet[i].setLayoutX(x);
            vet[i].setLayoutY(y);
            vet[i].setMinHeight(tamAltura);
            vet[i].setMinWidth(tamComprimento);
            vet[i].setFont(new Font(numFonte));
            add_pane(vet[i]);

            x += distancia;
        }
    }

    // configuracoes e funcoes da animacao a seguir:
    public void troca_botoes(int pos1, int pos2) {
        double posInicial1 = vet[pos1].getLayoutX(), posInicial2 = vet[pos2].getLayoutX();
        int distPerc = Math.abs((int) (posInicial2 - posInicial1) / 5);
        System.out.println(distPerc);

        for (int i = 0; i < 10; i++) {
            Platform.runLater(() -> vet[pos1].setLayoutY(vet[pos1].getLayoutY() + 5));
            Platform.runLater(() -> vet[pos2].setLayoutY(vet[pos2].getLayoutY() - 5));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (pos1 < pos2) {
            for (int i = 0; i < distPerc; i++) {
                Platform.runLater(() -> vet[pos1].setLayoutX(vet[pos1].getLayoutX() + 5));
                Platform.runLater(() -> vet[pos2].setLayoutX(vet[pos2].getLayoutX() - 5));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < distPerc; i++) {
                Platform.runLater(() -> vet[pos1].setLayoutX(vet[pos1].getLayoutX() - 5));
                Platform.runLater(() -> vet[pos2].setLayoutX(vet[pos2].getLayoutX() + 5));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Platform.runLater(() -> vet[pos1].setLayoutY(vet[pos1].getLayoutY() - 5));
            Platform.runLater(() -> vet[pos2].setLayoutY(vet[pos2].getLayoutY() + 5));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Button aux = vet[pos1];
        vet[pos1] = vet[pos2];
        vet[pos2] = aux;
    }

    public void move_botoes() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                int pos1 = 0, pos2 = 10;
                troca_botoes(pos1, pos2);

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}