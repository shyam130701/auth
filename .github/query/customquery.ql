import java

// Define a predicate to filter out the specific file
predicate isExcludedFile(string filePath) {
  exists(File f |
    f.getBaseName() = filePath
  )
}

// Main query to select all Java files except the excluded file
from
  JavaFile file
where
  not isExcludedFile("config") and // Exclude the specific file "config"
  file.hasSource()
select
  file
