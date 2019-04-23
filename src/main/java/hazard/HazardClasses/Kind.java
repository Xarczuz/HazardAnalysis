package hazard.HazardClasses;

public class Kind {
String kind;
int id;
public Kind( int id,String kind) {
	this.kind = kind;
	this.id = id;
}
public String getKind() {
	return kind;
}
public void setKind(String kind) {
	this.kind = kind;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
}
