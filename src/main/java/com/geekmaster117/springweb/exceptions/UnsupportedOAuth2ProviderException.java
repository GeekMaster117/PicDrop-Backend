package com.geekmaster117.springweb.exceptions;

public class UnsupportedOAuth2ProviderException extends RuntimeException
{
    public UnsupportedOAuth2ProviderException(String provider)
    {
        super("Unsupported authentication provider: " + provider);
    }
}
