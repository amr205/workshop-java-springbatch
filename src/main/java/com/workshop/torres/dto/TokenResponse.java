package com.workshop.torres.dto;

public record TokenResponse(String token, String tipo, long expiraEnMinutos) {
}
