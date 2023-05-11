package org.example;

public class MultiDimensionalArray {
    private int[][][] array;
    private int[] dimensions;

    public MultiDimensionalArray(int[] dimensions, int[] indexRanges) {
        this.dimensions = dimensions;
        this.array = new int[dimensions[0]][dimensions[1]][dimensions[2]];
        int idx = 0;
        for (int i = 0; i < dimensions[0]; i++) {
            for (int j = 0; j < dimensions[1]; j++) {
                for (int k = 0; k < dimensions[2]; k++) {
                    array[i][j][k] = idx++;
                    if (idx >= indexRanges[1]) idx = indexRanges[0];
                }
            }
        }
    }

    public int getElement(int i, int j, int k) {
        return array[i][j][k];
    }

    public int getElementAiliff(int i, int j, int k) {
        int[] indices = {i, j, k};
        int[] multipliers = {dimensions[1] * dimensions[2], dimensions[2], 1};
        int index = 0;
        for (int l = 0; l < 3; l++) {
            index += indices[l] * multipliers[l];
        }
        return array[index / (dimensions[1] * dimensions[2])]
                [(index % (dimensions[1] * dimensions[2])) / dimensions[2]]
                [index % dimensions[2]];
    }

    public int[] getVector(int index) {
        int[] vector = new int[3];
        vector[0] = index / (dimensions[1] * dimensions[2]);
        vector[1] = (index % (dimensions[1] * dimensions[2])) / dimensions[2];
        vector[2] = index % dimensions[2];
        return vector;
    }

    public void testAccessTime() {
        int numTrials = 10000000;

        // прямой доступ к элементам вектора
        long startBytes = Runtime.getRuntime().totalMemory();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numTrials; i++) {
            getElement(0, 1, 2);
        }
        long endTime = System.currentTimeMillis();
        long endBytes = Runtime.getRuntime().freeMemory();
        System.out.println("Прямой доступ к элементам вектора: " +
                (endTime - startTime) + " миллисекунд и " + (startBytes - endBytes) / 1024 + " килобайт памяти");

        // доступ посредством векторов Айлиффа
        startBytes = Runtime.getRuntime().totalMemory();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numTrials; i++) {
            getElementAiliff(0, 1, 2);
        }
        endTime = System.currentTimeMillis();
        endBytes = Runtime.getRuntime().freeMemory();
        System.out.println("Доступ посредством векторов Айлиффа: " +
                (endTime - startTime) + " миллисекунд и " + (startBytes - endBytes) / 1024 + " килобайт памяти");

        // метод определяющих векторов
        startBytes = Runtime.getRuntime().totalMemory();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numTrials; i++) {
            getVector(1234567);
        }
        endTime = System.currentTimeMillis();
        endBytes = Runtime.getRuntime().freeMemory();
        System.out.println("Метод определяющих векторов: " +
                (endTime - startTime) + " миллисекунд и " + (startBytes - endBytes) / 1024 + " килобайт памяти");

    }

    public static void main(String[] args) {
        int[] dimensions = {100, 100, 100};
        int[] indexRanges = {0, 1000};
        MultiDimensionalArray array = new MultiDimensionalArray(dimensions, indexRanges);
        array.testAccessTime();
    }
}
