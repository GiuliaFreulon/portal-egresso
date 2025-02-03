package com.ufma.PortalEgresso.exception;

public class BuscaVaziaRunTime extends RuntimeException{
    public BuscaVaziaRunTime() {
        super("Nenhum resultado para a busca");
    }
}
