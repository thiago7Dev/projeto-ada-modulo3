package org.example.processor;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface DuplicateProcessor {
    void processDuplicates(Map<String, List<Path>> duplicates);
}
