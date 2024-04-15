package com.example.sortanimation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;

public class Principal extends Application {
    // tela inicial que e apresentada
    AnchorPane pane;
    // botao que inicia o metodo para iniciar a ordenacao dos elementos
    ComboBox<String> comboBox;
    Button botao_inicio;
    Button botao_gerar_vetor;
    // vetor que e exibido
    private Button vet[];
    private static int TAMVET = 16;
    private static int TEMPO = 15;
    private static int DISTANCIA = 45;
    private int vetInt[];
    private int TL;
    private int vetPosButton[];

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
        configura_selecao_ordenacao();
        configura_botao_gerar_vetor();
        add_pane(botao_gerar_vetor);
        pane.getChildren().add(comboBox);
        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    // configuracoes de botoes e funcoes para insercao de elementos na tela a seguir:
    public void configura_botao_gerar_vetor() {
        botao_gerar_vetor = new Button();
        botao_gerar_vetor.setLayoutX(10);
        botao_gerar_vetor.setLayoutY(70);
        botao_gerar_vetor.setText("Gerar novo vetor");
        botao_gerar_vetor.setOnAction(e -> {
            geraVetor();
            renderiza_vetor();
        });
    }

    public void configura_selecao_ordenacao() {
        comboBox = new ComboBox<>();
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(150);
        comboBox.getItems().addAll("Bucket Sort", "Tim Sort");
        comboBox.setValue("Bucket Sort");
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
        vetPosButton = new int[TAMVET];
        for (int i = 0; i < TAMVET; i++) {
            vetPosButton[i] = i;
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

    public void renderiza_vetor() {
        for (int i = 0; i < TAMVET; i++) {
            int finalI = i;
            Platform.runLater(() -> vet[finalI].setText(vetInt[finalI] + ""));
        }
    }

    // posiciona o botao em uma posicao principal do vetor oficial
    public void posiciona_botao(int posVetor, int posDirecao) {
        int y = 200, x = 100;
        int posDirecaoX = x + DISTANCIA * posDirecao, posDirecaoY = y;
        if (vet[posVetor].getLayoutY() > 200)
            while (vet[posVetor].getLayoutY() > 200) {
                move_para_cima(posVetor, posVetor);
            }
        else
            while (vet[posVetor].getLayoutY() < 200)
                move_para_baixo(posVetor, posVetor);
        if (vet[posVetor].getLayoutX() > posDirecaoX)
            while (vet[posVetor].getLayoutX() > posDirecaoX) {
                move_para_esquerda(posVetor, posVetor);
            }
        else
            while (vet[posVetor].getLayoutX() < posDirecaoX) {
                move_para_direita(posVetor, posVetor);
            }
    }

    public void reorganiza_botoes() {
        Button aux[] = new Button[TAMVET];
        for (int i = 0; i < TAMVET; i++)
            aux[i] = vet[i];
        for (int i = 0; i < TAMVET; i++) {
            vet[i] = aux[vetPosButton[i]];
        }
        for (int i = 0; i < TAMVET; i++)
            vetPosButton[i] = i;
    }

    // funcoes de ordenacao Tim Sort e Bucket Sort a seguir:
    public void move_botoes() {
        String escolha = comboBox.getValue();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                if (escolha.equals("Bucket Sort")) {
                    bucketSort();
                } else if (escolha.equals("Tim Sort")) {
                    timSort(4);
                }
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
            insertion_sort(i, Math.min(i + n - 1, TAMVET - 1));
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++)
                move_para_cima(i * n, i * n + n - 1);
        }

        int espaco = n;
        for (int i = n; i > 1; i /= 2) {
            for (int j = 0; j < TAMVET; j += espaco * 2) {
                merge_sort(j, j + espaco - 1, j + espaco * 2 - 1);
                reorganiza_botoes();
            }
            espaco *= 2;
        }
        reorganiza_botoes();
        renderiza_vetor();
    }

    public void insertion_sort(int inicio, int fim) {
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

    public void merge_sort(int inicio, int meio, int fim) {
        int tam1 = meio - inicio + 1, tam2 = fim - meio;
        int[] vet1 = new int[tam1];
        int[] vet2 = new int[tam2];

        int moveInicio = inicio, moveFim = meio;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= i; j++)
                move_para_baixo(moveInicio, moveFim);
            moveInicio = moveFim + 1;
            moveFim = fim;
        }

        for (int x = 0; x < tam1; x++)
            vet1[x] = vetInt[inicio + x];

        for (int x = 0; x < tam2; x++)
            vet2[x] = vetInt[meio + 1 + x];

        int i = 0;
        int j = 0;
        int k = inicio;
        while (i < tam1 && j < tam2) {
            if (vet1[i] <= vet2[j]) {
                vetInt[k] = vet1[i];
                posiciona_botao(i + inicio, k);
                vetPosButton[k] = i + inicio;
                i++;
            } else {
                vetInt[k] = vet2[j];
                posiciona_botao(j + meio + 1, k);
                vetPosButton[k] = j + meio + 1;
                j++;
            }
            k++;
        }
        while (i < tam1) {
            vetInt[k] = vet1[i];
            posiciona_botao(i + inicio, k);
            vetPosButton[k] = i + inicio;
            k++;
            i++;
        }
        while (j < tam2) {
            vetInt[k] = vet2[j];
            posiciona_botao(j + meio + 1, k);
            vetPosButton[k] = j + meio + 1;
            k++;
            j++;
        }
    }

    public void bucketSort() {
        int numBuckets = new Random().nextInt(2, TAMVET);

        // Cria os baldes
        int baldes[][];
        baldes = new int[numBuckets][TAMVET];
        int tlBaldes[] = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++)
            tlBaldes[i] = 0;

        // Distribui os elementos nos baldes
        for (int i = 0; i < TAMVET; i++) {
            int indexBalde = (int) ((vetInt[i] / 100.0) * numBuckets);
            baldes[indexBalde][tlBaldes[indexBalde]++] = vetInt[i];
        }

        // Ordena baldes
        for (int i = 0; i < numBuckets; i++)
            insertionSort(baldes[i], tlBaldes[i]);

        // insere baldes no vetor
        int controleVet = 0;
        for (int i = 0; i < numBuckets; i++)
            for (int j = 0; j < tlBaldes[i]; j++)
                vetInt[controleVet++] = baldes[i][j];
        renderiza_vetor();
    }

    public void exibe_vetor() {
        for (int i = 0; i < TAMVET; i++)
            System.out.println(vetInt[i]);
    }

    public static void exibe_vetor(int array[], int TL) {
        for (int i = 0; i < TL; i++)
            System.out.println(array[i]);
    }

    public static void insertionSort(int[] array, int TL) {
        for (int i = 1; i < TL; ++i) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}