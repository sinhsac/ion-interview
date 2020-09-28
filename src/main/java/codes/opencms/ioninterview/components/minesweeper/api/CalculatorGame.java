package codes.opencms.ioninterview.components.minesweeper.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/minesweeper")
public class CalculatorGame {

    @PostMapping("/execute")
    public Map<String, List<String>> execute(@RequestBody Boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new RuntimeException("invalid data");
        }

        Integer maxRow = matrix.length;
        Integer maxCol = matrix[0].length;

        Map<String, List<String>> res = new LinkedHashMap<>();
        List<String> results = new ArrayList<>();
        res.put("result", results);
        for(Integer rowIndx = 0; rowIndx < maxRow; rowIndx++) {
            results.add(calculatorInfoMines(matrix, matrix[rowIndx], rowIndx, maxRow, maxCol));
        }

        return res;
    }

    private String calculatorInfoMines(Boolean[][] matrix, Boolean[] row, Integer rowIndx, Integer maxRow, Integer maxCol) {
        Integer[] cols = new Integer[maxCol];
        for(Integer colIndx = 0; colIndx < maxCol; colIndx++) {
            cols[colIndx] = countMines(matrix, rowIndx, colIndx, maxRow, maxCol);
        }
        return Arrays.stream(cols)
                .map(intVal -> String.valueOf(intVal))
                .collect(Collectors.joining(", "));
    }

    public Integer countMines(Boolean[][] matrix, Integer rowIndx, Integer colIndx, Integer maxRow, Integer maxCol) {
        Integer defaultCount = 0;
        defaultCount += containsMines(matrix, rowIndx - 1, colIndx, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx + 1, colIndx, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx, colIndx - 1, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx, colIndx + 1, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx - 1, colIndx - 1, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx + 1, colIndx + 1, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx - 1, colIndx + 1, maxRow, maxCol);
        defaultCount += containsMines(matrix, rowIndx + 1, colIndx - 1, maxRow, maxCol);
        return defaultCount;
    }

    private Integer containsMines(Boolean[][] matrix, Integer rowIndx, Integer colIndx, Integer maxRow, Integer maxCol) {
        if (rowIndx < 0 || colIndx < 0 || rowIndx >= maxRow || colIndx >= maxCol) {
            return 0;
        }
        if (matrix[rowIndx][colIndx] == true) {
            return 1;
        }
        return 0;
    }
}
