uniform sampler2D tex;
uniform vec2 size;
uniform float pixel;

void main()
{
 float dx = pixel*(1./size.x);
 float dy = pixel*(1./size.y);
 vec2 coord = vec2(dx*floor(gl_TexCoord[0].x/dx),
                   dy*floor(gl_TexCoord[0].y/dy));
 gl_FragColor = texture2D(tex, coord);
}