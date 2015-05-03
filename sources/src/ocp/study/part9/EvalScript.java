package ocp.study.part9;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EvalScript {

  public static void withResult() throws ScriptException {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("javascript");
    String script = "var x = '1Z0-810';" +
        "x.length";
    Object result = engine.eval(script);
    System.out.println(result);
  }

  public static void main(String[] args) {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine jsEngine = mgr.getEngineByName("javascript");
    try {
      Object result = jsEngine.eval("print('Hello, World!'); var x = 'test';  ");
      System.out.println("Result: " + result);

      withResult();
    } catch (ScriptException e) {
      e.printStackTrace();
    }
  }

}
