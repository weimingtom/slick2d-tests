varying vec3 varnormal, varpos;

void main()
{
	gl_Position = ftransform();
	varnormal = (gl_NormalMatrix * gl_Normal);
	varpos = (gl_ModelViewMatrix * gl_Vertex).xyz;
}