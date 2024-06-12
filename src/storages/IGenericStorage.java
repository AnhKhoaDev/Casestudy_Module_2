package storages;

import java.util.List;

public interface IGenericStorage<IGeneric> {
    void writeFile(List<IGeneric> iGenerics);
    List<IGeneric> readFile();
}
