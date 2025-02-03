package com.ufma.PortalEgresso.exceptions;

public class BuscaVaziaRunTime extends RuntimeException{
    public BuscaVaziaRunTime() {
        super("Nenhum resultado para a busca");
    }
}
