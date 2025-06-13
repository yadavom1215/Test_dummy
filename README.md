# FastAPI REST API Automation Framework

This project demonstrates an API automation framework using **Java, RestAssured, TestNG, and ExtentReports** to test a FastAPI service.

## 📂 Project Structure
See `src/test/java` for the tests, POJOs, config, and utilities.

## 🚀 How to Run

1. **Start your FastAPI app** locally on `http://localhost:8000/api` or change the base URI in `ConfigReader.java`.
2. Run tests:
   ```bash
   mvn clean test
   ```
3. View the HTML report at `reports/AutomationReport.html`.

## ⚙️ CI/CD

GitHub Actions pipeline in `.github/workflows/ci.yml` runs tests on every push to **main**.

## ✍️ Author
Manoj
