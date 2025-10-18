// StudentManager.java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentManager {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        students.add(s);
    }

    public boolean updateStudent(int id, String name, Integer age, String course) {
        Optional<Student> opt = students.stream().filter(st -> st.getId() == id).findFirst();
        if (opt.isPresent()) {
            Student s = opt.get();
            if (name != null && !name.isBlank()) s.setName(name);
            if (age != null) s.setAge(age);
            if (course != null && !course.isBlank()) s.setCourse(course);
            return true;
        }
        return false;
    }

    public boolean removeStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }

    public Student searchById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<Student> searchByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) result.add(s);
        }
        return result;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public boolean idExists(int id) {
        return students.stream().anyMatch(s -> s.getId() == id);
    }
}
