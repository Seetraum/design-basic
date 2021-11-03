package com.test.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class ScriptTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        runJsScript();
        runPythonScript();
    }

    public static void runJsScript() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try (FileReader fr = new FileReader(ScriptTest.class.getResource("/jstest.js").getFile())) {
            engine.eval(fr);
            Invocable invoke = (Invocable) engine;
            double val = (double) invoke.invokeFunction("test");
            System.out.println(" js  result = " + val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runPythonScript() {
        try (FileReader fr = new FileReader(ScriptTest.class.getResource("/pytest.py").getFile())) {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("python");
            engine.eval(fr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
