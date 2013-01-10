varying vec2 texcoord;
 
void main(void)
{
    gl_Position = ftransform();
    texcoord = vec2(ftransform() * 0.5 + 0.5);
}