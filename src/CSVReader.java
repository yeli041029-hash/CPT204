import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * CSVReader 用于读取 candidates_A/B/C.csv 文件。
 * 每一行数据会被转换成一个 Location 对象，
 * 最后返回一个 ArrayList<Location>。
 */
public class CSVReader {

    /*
     * 读取候选地点 CSV 文件。
     * filePath 是文件路径，例如 "data/candidates_A.csv"。
     */
    public static ArrayList<Location> readCandidateFile(String filePath) {

        // 用来保存读取到的所有地点
        ArrayList<Location> locations = new ArrayList<>();

        // 使用 BufferedReader 按行读取 CSV 文件
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirstLine = true;

            // 一行一行读取，直到文件结束
            while ((line = br.readLine()) != null) {

                // 跳过空行
                if (line.trim().isEmpty()) {
                    continue;
                }

                // 如果第一行是表头，就跳过
                if (isFirstLine) {
                    isFirstLine = false;

                    if (line.toLowerCase().contains("location")) {
                        continue;
                    }
                }

                // CSV 每一列用逗号分隔
                String[] parts = line.split(",");

                // 正常情况下需要至少两列：location_id 和 priority_score
                if (parts.length >= 2) {
                    String locationId = parts[0].trim();

                    // 将字符串形式的分数转换成 double，方便后面排序
                    double priorityScore = Double.parseDouble(parts[1].trim());

                    // 创建 Location 对象，并加入列表
                    locations.add(new Location(locationId, priorityScore));
                }
            }

        } catch (IOException e) {
            // 如果文件读取失败，打印错误信息
            System.out.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        // 返回读取到的地点列表
        return locations;
    }
}