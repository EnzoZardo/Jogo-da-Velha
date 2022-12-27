package com.example.jogo_da_velha;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private int jogada = 0;
    private final String VELHA = "VELHA";
    private final HashMap<Integer, int[]> VERIFICACOES_LCD = new HashMap<>() {{
       put(1, new int[]{1, 3, 4});
       put(2, new int[]{3});
       put(3, new int[]{2, 3});
       put(4, new int[]{1});
       put(7, new int[]{1});
    }};
    private final HashMap<String, String> ocupados = new HashMap<>() {{
        put("b1", "vazio");
        put("b2", "vazio");
        put("b3", "vazio");
        put("b4", "vazio");
        put("b5", "vazio");
        put("b6", "vazio");
        put("b7", "vazio");
        put("b8", "vazio");
        put("b9", "vazio");
    }};

    @FXML
    protected void realizaJogada(ActionEvent event) {
        if ((ocupados.get(((Button) event.getTarget()).getId())).equals("vazio")) {
            if (jogada % 2 == 0) {
                setButton(((Button) event.getTarget()), "X", "#ff0000", ocupados);
            } else {
                setButton(((Button) event.getTarget()), "O", "#0000ff", ocupados);
            }
            jogada++;
            if(!(vencedor(VERIFICACOES_LCD).equals(VELHA)) || jogada == 9) {
                fimDeJogo(event, VERIFICACOES_LCD);
            }
        }
    }

    protected String verificaVitoria(int indexInicial, int intervalo) {
        if (!(ocupados.get("b" + String.valueOf(indexInicial)).equals("vazio")) && ocupados.get("b" + String.valueOf(indexInicial)).equals(ocupados.get("b" + String.valueOf(indexInicial + intervalo))) && ocupados.get("b" + String.valueOf(indexInicial)).equals(ocupados.get("b" + String.valueOf(indexInicial + intervalo * 2)))) {
            return ocupados.get("b" + String.valueOf(indexInicial));
        }
        return VELHA;
    }

    protected String vencedor(HashMap<Integer, int[]> where) {
        for (Map.Entry<Integer, int[]> pair : where.entrySet()) {
            for(int el : pair.getValue()) {
                if (!(verificaVitoria(pair.getKey(), el).equals(VELHA))) {
                    return verificaVitoria(pair.getKey(), el);
                }
            }
        }
        return VELHA;
    }

    protected void setButton(Button button, String txt, String color, HashMap who) {
        button.setText(txt);
        button.setStyle("-fx-background-color: " + color);
        who.put(button.getId(), txt);
    }

    protected void close(ActionEvent event) {
        ((Stage)((Button) event.getTarget()).getScene().getWindow()).close();
    }

    protected void restart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 383, 383);
        ((Stage)((Button) event.getTarget()).getScene().getWindow()).setScene(scene);
    }
    
    protected void fimDeJogo(ActionEvent event, HashMap VERIFICACOES_LCD) {
        Alert endGame = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnNew = new ButtonType("NOVO JOGO"), btnClose = new ButtonType("SAIR");
        endGame.setTitle("Fim de jogo");
        endGame.setHeaderText((vencedor(VERIFICACOES_LCD).equals(VELHA))? "Deu velha!":"O jogador > " + vencedor(VERIFICACOES_LCD) + " < venceu!!!");
        endGame.setContentText("Deseja jogar novamente?");
        endGame.getButtonTypes().setAll(btnNew, btnClose);
        endGame.showAndWait().ifPresent(b -> {
            if (b == btnNew) {
                try {
                    restart(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (b == btnClose) {
                close(event);
            }
        });
    }
}

/*


 */