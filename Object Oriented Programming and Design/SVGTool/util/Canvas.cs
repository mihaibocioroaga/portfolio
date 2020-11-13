using System;
using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Canvas
    {
        public float Width { get; }
        public float Height { get; }
        List<IShape> Shapes { get; }

        /// <summary>
        /// Generate a new Canvas object, which is a container for a list of Shapes.
        /// </summary>
        /// The Canvas provides methods that can convert the list of Shapes into a renderable SVG file.
        /// <param name="width">Canvas width in pixels</param>
        /// <param name="height">Canvas height in pixels</param>
        public Canvas(int width, int height)
        {
            Width = width;
            Height = height;
            Shapes = new List<IShape>();
        }

        /// <summary>
        /// Add a new shape to the canvas.
        /// </summary>
        /// <param name="shape">Shape to add to the canvas.</param>
        public void Add(IShape shape)
        {
            Shapes.Add(shape);
        }

        /* 
         * Methods for manipulation of Z index
         */

        /// <summary>
        /// Bring specified shape above every other shape.
        /// </summary>
        /// <param name="index"></param>
        public void BringForward(int index)
        {
            IShape shape = Shapes[index];
            Shapes.Remove(shape);
            Shapes.Add(shape);
        }

        /// <summary>
        /// Send specified shape behind every other shape.
        /// </summary>
        /// <param name="index"></param>
        public void SendBackward(int index)
        {
            IShape shape = Shapes[index];
            Shapes.Remove(shape);
            Shapes.Insert(0, shape);
        }

        /// <summary>
        /// Move specified shape to another specified index within the canvas.
        /// </summary>
        /// <param name="oldIndex">Current index of shape to be moved</param>
        /// <param name="newIndex">Index of new position</param>
        public void Move(int oldIndex, int newIndex)
        {
            IShape shape = Shapes[oldIndex];
            Shapes.Remove(shape);
            Shapes.Insert(newIndex, shape);
        }

        public string Export()
        {
            //Flip from LIFO to LILO, which will produce more intuitive z-indexing according to list order.
            //Then switch to a Stack for export so we can pop.
            Shapes.Reverse(); 
            Stack<IShape> shapeStack = new Stack<IShape>(Shapes);

            StringBuilder canvas = new StringBuilder($"<svg width=\"{Width}\" height=\"{Height}\" xmlns = \"http://www.w3.org/2000/svg\" version = \"1.1\" >" +
                Environment.NewLine);

            while (shapeStack.Count > 0)
            {
                canvas.Append(shapeStack.Pop().ToSVGString());
                canvas.Append(Environment.NewLine);
            }

            canvas.Append($"</svg>");

            return canvas.ToString();
        }
    }
}