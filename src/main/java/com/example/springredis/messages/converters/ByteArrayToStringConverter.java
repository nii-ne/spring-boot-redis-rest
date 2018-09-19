package com.truemoney.api.paymentcomposite.messages.converters;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.io.UnsupportedEncodingException;

//This class is used for convert rabbitmessage from  byte[] to String
public class ByteArrayToStringConverter extends SimpleMessageConverter {
    private static final String DEFAULT_CHARSET = "UTF-8";

    private String charset;

    public ByteArrayToStringConverter() {
        this.charset = DEFAULT_CHARSET;
    }

    public ByteArrayToStringConverter(String charset) {
        this.charset = charset;
    }

    @Override
    public Object fromMessage(final Message message) {
        final Object content = super.fromMessage(message);
        try {
            if (content instanceof byte[]) {
                return new String((byte[]) content, charset);
            }
        } catch (final UnsupportedEncodingException e) {
            throw new MessageConversionException("failed to convert text-based Message content", e);
        }
        return content;
    }
}
