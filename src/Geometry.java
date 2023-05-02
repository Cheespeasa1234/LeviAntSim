public class Geometry {
    public static final int triangleScale = 5;
    public static final int[] triangleXValues = { 0, triangleScale, triangleScale * 2 };
    public static final int[] triangleYValues = { 0, triangleScale * 2, 0 };

    public static int[] transformArray(int[] arr, int add) {
        int[] transformed = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            transformed[i] = arr[i] + add;
        return transformed;
    }
}
