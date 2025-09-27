package com.example.random_picker;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "data")
public class DataConfig {

    private List<String> selectionPool;

    public List<String> getSelectionPool() {
        return selectionPool;
    }

    public void setSelectionPool(List<String> selectionPool) {
        this.selectionPool = selectionPool;
    }
}


