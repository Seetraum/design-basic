function test() {
    var start = new Date().getTime();
    for (var i = 0; i < 5; i++) {
        test1();
    }
    return new Date().getTime() - start;
}

function test1() {
    var start = new Date().getTime();
    var r = 50;
    var a = 0.0;
    for (var i = 0; i < r; i++) {
        for (var j = 0; j < r; j++) {
            a = a + 3.1415926;
        }
    }
    return new Date().getTime() - start;
}