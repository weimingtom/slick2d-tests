varying vec3 varnormal, varpos;

uniform vec3 diffuse_color;
uniform float alpha;

uniform float edge;
uniform float mid;

void main()
{
	vec3 n, lightDir;
	float intensity, factor;
	
	vec4 color = vec4(diffuse_color, alpha);
	
	n = normalize(varnormal);
	lightDir = normalize(gl_LightSource[0].position.xyz - varpos);
	intensity = max(dot(n, lightDir), 0.0);
	
	if (intensity > mid)
		factor = 1.0;
	else if (intensity > edge)
		factor = 0.7;
	else
		factor = 0.0;
	
	gl_FragColor = color *vec4(factor, factor, factor, 1);

}
