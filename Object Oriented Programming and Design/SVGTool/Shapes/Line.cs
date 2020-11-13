using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Line : StyleManager, IShape
    {
        public Point Position { get; set; }
        public Point EndPosition { get; set; }
        public Line(float x1, float y1, float x2, float y2)
        {
            Position = new Point(x1, y1);
            EndPosition = new Point(x2, y2);
            StyleElements = new HashSet<IStyleElement>();
        }
        public string ToSVGString()
        {
            StringBuilder exportString = new StringBuilder($"<line x1=\"{Position.X}\" y1=\"{Position.Y}\" x2=\"{EndPosition.X}\" y2=\"{EndPosition.Y}\" ");
            ApplyStyleElements(exportString, StyleElements);
            exportString.Append(" />");
            return exportString.ToString();
        }
    }
}
