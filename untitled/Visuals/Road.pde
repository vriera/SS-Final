class Road {
  Node start;
  Node end;
  String dir;
  int id;
  
  Road(int id, Node s, Node e, String d) {
    this.id = id;
    start = s;
    end = e;
    dir = d;
  }
}
