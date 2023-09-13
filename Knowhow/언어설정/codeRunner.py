import subprocess, os, sys
import time

class Path:
    home = r"C:\Users\Glory\Desktop\codeZip\VSC"
    exe = fr"{home}\bat\exe"
    io = fr"{home}\io"
    stdInput = fr"{io}\input.txt"
    stdOutput = fr"{io}\result.txt"
    openInput = open(stdInput, "r")
    openOutput = open(stdOutput, "w")
    def __init__(self):
        if not os.path.exists(self.exe):
            os.makedirs(self.exe)
        if not os.path.exists(self.io):
            os.makedirs(self.io)
        if not os.path.exists(self.stdInput):
            with open(self.stdInput, "w"): None

class Language:
    def __init__(self, buildTool, executableFile, outputs,
                 buildExtra=None, runExtra=None):
        if not buildExtra: buildExtra = []
        if not runExtra: runExtra = []
        self.buildTool = buildTool
        self.executableFile = executableFile
        self.outputs = outputs
        self.needCompile = len(outputs) != 0
        self.buildExtra = buildExtra
        self.runExtra = runExtra

lang: {str: Language} = {
    "c"     : Language(["gcc"]        , "a.exe"     , ["a.exe"]),
    "cpp"   : Language(["g++"]        , "a.exe"     , ["a.exe"]),
    "rb"    : Language(["ruby"]       , "main.rb"   , []),
    "rs"    : Language(["rustc"]      , "main.exe"  , ["main.exe", "main.pdb"]),
    "java"  : Language(["java"]       , "Main.java" , []),
    "swift" : Language(["swiftc"]     , "main.exe"  , ["main.exe", "main.exp", "main.lib"]),
    "kt"    : Language(["kotlinc"]    , "MainKt.jar", ["MainKt.jar"], ["-d", fr"{Path.exe}\MainKt.jar"], ["java", "-jar"]),
    "go"    : Language(["go", "build"], "main.exe"  , ["main.exe"]),
    "py"    : Language(["python"]     , "main.py"   , []),
    "js"    : Language(["node"]       , "main.js"   , []),
    "cs"    : Language(["dotnet", "script"], "main.cs", [])
}

filePath = sys.argv[1]
fileName = sys.argv[1].split("\\")[-1]
extension = sys.argv[1].split(".")[-1]
P = Path()
def run(extension):
    if extension not in lang:
        print("Not Support")
        input()
        exit(0)
    info: Language = lang[extension]
    fail = False
    if info.needCompile:
        runArgs = [*info.buildTool, filePath, *info.buildExtra]
        compile_process = subprocess.run(runArgs, stderr=subprocess.PIPE, cwd=P.exe, shell=True)
        fail = compile_process.returncode != 0

    if not info.needCompile or compile_process.returncode == 0:
        runArgs = info.runExtra
        if info.needCompile:
            exePath = P.exe
            exeFile = info.executableFile
            runArgs.append(fr"{exePath}\{exeFile}")
        else:
            exePath = filePath.split("\\")[:-1]
            exeFile = fileName
            runArgs += info.buildTool
            runArgs.append('\\'.join(exePath)+fr"\{exeFile}")
        print("Running")
        startTime = time.time()
        with P.openInput as inputFile, P.openOutput as outputFile:
            process = subprocess.run(runArgs, stdin=inputFile, stdout=outputFile, stderr=outputFile)
        endTime = time.time()
        print(f"Process Running Time : {endTime-startTime}")
        fail = process.returncode != 0

    delete(info.outputs)
    if fail:
        print("Compilation Failed.")
        print("Error Message:", compile_process.stderr.decode('utf-8'))
    else:
        print("success")

def delete(outputs):
    for output in outputs:
        if not os.path.exists(fr"{P.exe}/{output}"): continue
        os.remove(fr"{P.exe}/{output}")

if __name__ == "__main__":
    run(extension)