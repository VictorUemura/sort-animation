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
    private static int TAMVET = 12;
    private int vetInt[];
    private int TL;

    public static void main(String[] args) {
        launch(args);
    }

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

    public void configura_exibicao_vetor() {
        // configura exibicao do vetor
        int x = 100, y = 200, tamAltura = 40, tamComprimento = 40, numFonte = 14, distancia = 42;
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

    public void geraVetor() {
        TL = 0;
        vetInt = new int[TAMVET];
        while (TL < TAMVET)
            vetInt[TL++] = geraNumero(1, 100);
    }

    public int geraNumero(int min, int max) {
        Random random = new Random();
        int value;
        do {
            value = random.nextInt(min, max);
        } while (existe(value));
        return value;
    }

    public boolean existe(int num) {
        int i = 0;
        while (i < TL && vetInt[i] != num)
            i++;
        return i != TL;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        configura_botao_inicio();
        configura_pane();
        geraVetor();
        configura_exibicao_vetor();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void move_botoes() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Button aux = vet[0];
                vet[0] = vet[1];
                vet[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}