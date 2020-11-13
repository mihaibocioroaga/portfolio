using System;
using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    class StrokeWidth : IStyleElement
    {
        public float Width { get; set; }

        public StrokeWidth(float strokeWidth)
        {
            Width = strokeWidth;
        }
        public string ToSVGStyleString() => $"stroke-width:{Width}";
    }
}